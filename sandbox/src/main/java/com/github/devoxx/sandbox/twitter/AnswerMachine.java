package com.github.devoxx.sandbox.twitter;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.operators.Throttler;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.Tools;
import com.github.devoxx.sandbox.twitter.TwitterFun.TimelineMessTwitterFun;
import rx.Observable;
import twitter4j.Status;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AnswerMachine {
    private static AtomicLong answerId = new AtomicLong();
    public static final TimelineMessTwitterFun twitterClient = TwitterFun.client();
    public static final ObservableServerApi api = new ApiFactory().reliablePartner().observable();

    public static Observable<Status> observe(Observable<Status> status) {
        return status.flatMap(AnswerMachine::answer);
    }

    private static final Random random = new Random();

    private static Observable<Status> answer(Status status) {
        return Observable.just(status).map(Status::getText)
                .map(t -> t.toLowerCase().trim().replaceAll("[\\W]", ""))
                .doOnNext(System.out::println)
                .concatMap(term -> api.search(term).flatMap(movie ->
                        api.actors(movie.id)
                                .map(actors -> actors.get(random.nextInt(actors.size())))
                                .map(actor -> buildFoundTweet(status, movie, actor)))
                        .onErrorResumeNext(ex -> {
                            return api.random().map(movie ->
                                    buildNotFoundTweet(status, movie));
                        }))
                .doOnEach(Tools::threadInfo)
                .onBackpressureBlock()
                .lift(new Throttler<String>(5, TimeUnit.SECONDS))
                .map(s -> (Status) new DumbStatus("dwursteisen", s)) // TODO: replace me
              //  .flatMap(tweet -> twitterClient.updateStatus(tweet)
              //          .doOnError(t -> t.printStackTrace(System.err))
              //          .onErrorResumeNext(Observable.empty()))
                .doOnEach(Tools::threadInfo);
    }

    private static String buildNotFoundTweet(Status status, Movie movie) {
        return String.format("@%s : Pas trouvé ! Essaye avec %s (answer=%s)",
                status.getUser().getScreenName(),
                movie.title, answerId.getAndIncrement());
    }

    private static String buildFoundTweet(Status status, Movie movie, Actor actor) {
        String result = String.format("@%s : %s avec %s %s (answer=%s)",
                status.getUser().getScreenName(),
                movie.title,
                actor.firstName, actor.lastName,
                answerId.getAndIncrement());
        return result;
    }
}
