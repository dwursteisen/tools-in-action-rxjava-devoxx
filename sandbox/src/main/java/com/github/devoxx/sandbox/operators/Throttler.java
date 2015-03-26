package com.github.devoxx.sandbox.operators;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by david on 26/03/15.
 */
public class Throttler<T> implements Observable.Operator<T, T> {
    private final long time;
    private final TimeUnit unit;
    private final Scheduler scheduler;

    public Throttler(long time, TimeUnit unit, Scheduler scheduler) {
        this.time = time;
        this.unit = unit;
        this.scheduler = scheduler;
    }

    public Throttler(long time, TimeUnit unit) {
        this(time, unit, Schedulers.computation());
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        Scheduler.Worker worker = scheduler.createWorker();
        return new Subscriber<T>(subscriber) {

            @Override
            public void onStart() {
                request(1);
            }

            @Override
            public void onCompleted() {
                worker.schedule(subscriber::onCompleted, time, unit);
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(T integer) {
                worker.schedule(() -> {
                    subscriber.onNext(integer);
                    request(1);
                }, time, unit);
            }
        };
    }
}
