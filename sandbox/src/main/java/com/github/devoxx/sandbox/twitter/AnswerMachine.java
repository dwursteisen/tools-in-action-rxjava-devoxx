package com.github.devoxx.sandbox.twitter;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
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
        return status.flatMap(AnswerMachine::answer);
    }

    private static Observable<Status> answer(Status status) {
        return Observable.just(status).map(Status::getText)
                .flatMap(statusText -> Observable.from(statusText.split("[,\\s]+")))
                .filter(term -> !Arrays.asList("#DV15TEST", "#DVFR15", "@RxJava", "tia").contains(term))
                .filter(term -> term.matches("\\w+"))
                .flatMap(term -> api.movie(term).onErrorResumeNext(Observable.empty()))
                .timeout(1, SECONDS)
                .doOnEach(Tools::threadInfo)
                .flatMap(movie ->
                        twitterClient.updateStatus(String.format("@%s : Found it '%s' (answer=%s)", status.getUser().getScreenName(), movie.title, answerId.getAndIncrement()))
                                .doOnError(t -> t.printStackTrace(System.err))
                                .onErrorResumeNext(Observable.empty()))
                .doOnEach(Tools::threadInfo);
    }
}
