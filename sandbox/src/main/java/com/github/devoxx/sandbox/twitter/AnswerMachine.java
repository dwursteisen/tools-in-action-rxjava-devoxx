package com.github.devoxx.sandbox.twitter;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.operators.Throttler;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.Tools;
import com.github.devoxx.sandbox.twitter.TwitterFun.TimelineMessTwitterFun;
import rx.Observable;
import twitter4j.Status;

public class AnswerMachine {
    private static AtomicLong answerId = new AtomicLong();
    public static final TimelineMessTwitterFun twitterClient = TwitterFun.client();
    public static final ObservableServerApi api = new ApiFactory().reliablePartner().observable();

    public static Observable<Status> observe(Observable<Status> status) {
        return status
                .onBackpressureBuffer()
                .lift(new Throttler<Status>(5, TimeUnit.SECONDS))
                .flatMap(AnswerMachine::answerToTweet);
    }

    private static final Random random = new Random();

    private static Observable<Status> answerToTweet(Status status) {
        return Observable.just(status).map(Status::getText)
                .map(t -> t.toLowerCase().trim().replaceAll("[\\W]", ""))
                .doOnNext(System.out::println)
                .concatMap(term -> buildTweet(term, status))
                .doOnEach(Tools::threadInfo)
                .flatMap(AnswerMachine::postTweet)
                .doOnEach(Tools::threadInfo);
    }

    private static Observable<String> buildTweet(String term, Status status) {
        return api.search(term).flatMap(movie ->
                api.actors(movie.id)
                        .map(actors -> actors.get(random.nextInt(actors.size())))
                        .map(actor -> buildFoundTweet(status, movie, actor)))
                .onErrorResumeNext(ex -> {
                    return api.random().map(movie ->
                            buildNotFoundTweet(status, movie)).onErrorReturn(e -> "Issue with the server. Please try again !");
                });
    }

    private static Observable<Status> postFakeTweet(String tweet) {
        return Observable.just(new DumbStatus("dwursteisen", tweet)).cast(Status.class);
    }

    private static Observable<Status> postTweet(String tweet) {
        return twitterClient.updateStatus(tweet)
                .doOnError(t -> t.printStackTrace(System.err))
                .onErrorResumeNext(Observable.empty());
    }

    private static String buildNotFoundTweet(Status status, Movie movie) {
        return String.format("@%s : Not found... Try with %s (answer=%s)",
                status.getUser().getScreenName(),
                movie.title, answerId.getAndIncrement());
    }

    private static String buildFoundTweet(Status status, Movie movie, Actor actor) {
        return String.format("@%s : %s is a good movie with %s %s (answer=%s)",
                status.getUser().getScreenName(),
                movie.title,
                actor.firstName, actor.lastName,
                answerId.getAndIncrement());
    }
}
