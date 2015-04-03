package com.github.devoxx.sandbox.twitter;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.twitter.TwitterFun.TimelineMessTwitterFun;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import rx.Observable;
import twitter4j.Status;

public class AnswerMachine {
    private static AtomicLong answerId = new AtomicLong();
    public static final TimelineMessTwitterFun twitterClient = TwitterFun.client();
    public static final ObservableServerApi api = new ApiFactory().reliablePartner().observable();

    public static Observable<Status> observe(Observable<Status> status) {
        return status.flatMap(AnswerMachine::answer);
    }

    private static Observable<Status> answer(Status status) {
        return Observable.just(status).map(Status::getText)
                .flatMap(AnswerMachine::termExtraction)
                .flatMap(term ->
                        api.movie(term)
                                .onErrorResumeNext(Observable.empty())
                                .timeout(1, SECONDS))
                .flatMap(movie ->
                        api.synopsis(movie.id)
                                .onErrorResumeNext(Observable.empty())
                                .timeout(1, SECONDS)
                                .flatMap(synopsis -> RxOkHttp.INSTANCE.download(synopsis.posterUrl))
                                .flatMap(inputStream ->
                                        twitterClient.updateStatus(
                                                String.format("@%s : Found it '%s' (answer=%s)", status.getUser().getScreenName(),
                                                        movie.title,
                                                        answerId.getAndIncrement()),
                                                "poster",
                                                inputStream
                                        ))
                                .doOnError(t -> t.printStackTrace(System.err))
                                .onErrorResumeNext(Observable.empty())
                )
                ;
    }

    private static Observable<String> termExtraction(String statusText) {
        return Observable.from(statusText.split("[,\\s]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
                .filter(term -> !Arrays.asList("#DV15TEST", "#DVFR15", "@RxJava", "tia").contains(term))
                .map(term -> term.replaceAll("[\\s\"]", "").toLowerCase())
                .filter(term -> term.matches("\\w+"));
    }

    static class RxOkHttp {
        public static final RxOkHttp INSTANCE = new RxOkHttp();

        private final OkHttpClient okHttp = new OkHttpClient();

        private RxOkHttp() {
        }

        public Observable<InputStream> download(String url) {
            try {
                Request poster = new Request.Builder()
                        .get()
                        .url(url)
                        .build();

                InputStream responseBody = okHttp.newCall(poster)
                        .execute()
                        .body()
                        .byteStream();
                return Observable.just(responseBody);
            } catch (IOException ioe) {
                return Observable.error(ioe);
            }
        }
    }
}
