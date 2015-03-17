package com.github.devoxx.sandbox.slides;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by david.wursteisen on 11/03/2015.
 */
public class H1_BackPressure {

    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1000)
                .lift(new Throttler<>(1, TimeUnit.SECONDS))
                .toBlocking()
                .forEach(System.out::println);

    }

    private static class Throttler<T> implements Observable.Operator<T, T> {
        private final long time;
        private final TimeUnit unit;
        private final Scheduler scheduler;

        public Throttler(long time, TimeUnit unit, Scheduler scheduler) {
            this.time = time;
            this.unit = unit;
            this.scheduler = scheduler;
        }

        private Throttler(long time, TimeUnit unit) {
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
}
