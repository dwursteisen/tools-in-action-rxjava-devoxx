package com.github.devoxx.sandbox.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import rx.Observable;
import twitter4j.Status;

public class AnswerMachine {
    private static final AtomicLong answerId = new AtomicLong(1);
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
                .filter(s -> !s.matches(".*\\(k\\d+\\).*"))
                .map(t -> t.toLowerCase().trim().replaceAll("[\\W]", ""))
                .doOnNext(System.out::println)
                .concatMap(term -> buildTweet(term, status))
                .doOnEach(Tools::threadInfo)
                .flatMap(AnswerMachine::postTweet)
                .doOnEach(Tools::threadInfo);
    }

    private static Observable<InternalTweet> buildTweet(String term, Status status) {
        return api.search(term).flatMap(movie ->
                api.actors(movie.id)
                        .map(actors -> actors.get(random.nextInt(actors.size())))
                        .map(actor -> buildFoundTweet(status, movie, actor))
                        .zipWith(api.synopsis(movie.id).flatMap(s -> RxOkHttp.INSTANCE.download(s.posterUrl)), InternalTweet::new))

                .onErrorResumeNext(ex -> {
                    return api.random().map(movie ->
                            buildNotFoundTweet(status, movie)).onErrorReturn(e -> status.getUser() + " : Oups ! Try again ! Github: https://github.com/dwursteisen/tools-in-action-rxjava-devoxx")
                            .map(InternalTweet::new);
                });
    }

    private static Observable<Status> postFakeTweet(InternalTweet tweet) {
        return Observable
                .just(new DumbStatus("dwursteisen", tweet.status + tweet.poster.map(i -> " with poster").orElse(" without poster")))
                .cast(Status.class);
    }

    private static Observable<Status> postTweet(InternalTweet tweet) {
        return tweet.poster.map(i -> twitterClient.updateStatus(tweet.status, "poster", i))
                .orElse(twitterClient.updateStatus(tweet.status))
                .doOnError(t -> t.printStackTrace(System.err))
                .onErrorResumeNext(Observable.empty());
    }

    private static String buildNotFoundTweet(Status status, Movie movie) {
        return String.format("@%s : Not found. Try with %s | Github: https://github.com/dwursteisen/tools-in-action-rxjava-devoxx #DevoxxFr (k%s)",
                status.getUser().getScreenName(),
                movie.title, answerId.getAndIncrement());
    }

    private static String buildFoundTweet(Status status, Movie movie, Actor actor) {
        return String.format("@%s : %s is a good movie with %s %s | Github: https://github.com/dwursteisen/tools-in-action-rxjava-devoxx #DevoxxFr (k%s)",
                status.getUser().getScreenName(),
                movie.title,
                actor.firstName, actor.lastName,
                answerId.getAndIncrement());
    }

    private static class InternalTweet {
        private final String status;
        private final Optional<InputStream> poster;

        private InternalTweet(String status, InputStream poster) {
            this.status = status;
            this.poster = Optional.of(poster);
        }

        public InternalTweet(String status) {
            this.status = status;
            this.poster = Optional.empty();
        }

        @Override
        public String toString() {
            return "InternalTweet{" +
                    "status='" + status + '\'' +
                    '}';
        }
    }

    static class RxOkHttp {
        public static final RxOkHttp INSTANCE = new RxOkHttp();

        private final OkHttpClient okHttp = new OkHttpClient();

        private RxOkHttp() {
        }

        public Observable<InputStream> download(String url) {
            return Observable.create(subscriber -> {

                try {
                    Request poster = new Request.Builder()
                            .get()
                            .url(url)
                            .build();

                    InputStream responseBody = okHttp.newCall(poster)
                            .execute()
                            .body()
                            .byteStream();
                    subscriber.onNext(responseBody);
                    subscriber.onCompleted();
                } catch (IOException ioe) {
                    subscriber.onError(ioe);
                }
            });
        }
    }
}
