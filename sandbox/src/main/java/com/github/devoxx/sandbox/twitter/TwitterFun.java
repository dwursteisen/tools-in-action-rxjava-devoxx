package com.github.devoxx.sandbox.twitter;

import java.util.Arrays;
import rx.Observable;
import rx.subjects.PublishSubject;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterFun {
    private final TwitterStream twitterStream;
    private Observable<Status> twitterObservable;

    private TwitterFun(Observable<Status> twitterObservable, TwitterStream twitterStream) {
        this.twitterObservable = twitterObservable;
        this.twitterStream = twitterStream;
    }

    public static TwitterFun prepare() {
        PublishSubject<Status> twitterObservable = PublishSubject.create();

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                twitterObservable.onNext(status);
            }

            @Override
            public void onException(Exception ex) {
                twitterObservable.onError(ex);
            }
        });
        return new TwitterFun(
                twitterObservable.doOnUnsubscribe(twitterStream::cleanUp),
                twitterStream);
    }

    public Observable<Status> sample() {
        return twitterObservable
                .doOnSubscribe(twitterStream::sample)
                .doOnSubscribe(() -> System.out.println("Tweets stream sampling started"))
                ;
    }

    public Observable<Status> track(String... singleWords) {
        FilterQuery query = new FilterQuery();
        query.track(singleWords);
        return twitterObservable
                .doOnSubscribe(() -> twitterStream.filter(query))
                .doOnSubscribe(() -> System.out.printf("Looking for tweets with : %s%n", Arrays.deepToString(singleWords)))
                ;
    }
}
