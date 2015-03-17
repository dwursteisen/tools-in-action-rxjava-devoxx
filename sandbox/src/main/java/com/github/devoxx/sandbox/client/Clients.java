package com.github.devoxx.sandbox.client;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 *
 */
public class Clients {
    /**
     * This is an artificial source to demonstrate an infinite stream that emits randomly
     */
    public static Observable<Integer> sending_lot_of_refresh_queries() {
        return Observable.create((Subscriber<? super Integer> s) -> {
            int i = 0;
            while (!s.isUnsubscribed()) {
                s.onNext(i++);
                try {
                    // sleep for a random amount of time
                    // NOTE: Only using Thread.sleep here as an artificial demo.
                    Thread.sleep((long) (Math.random() * 10));
                } catch (Exception e) {
                    // do nothing
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
