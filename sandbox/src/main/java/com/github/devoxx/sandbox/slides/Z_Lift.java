package com.github.devoxx.sandbox.slides;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by david.wursteisen on 11/03/2015.
 */
public class Z_Lift {

    public static void main(String[] args) {
        Observable.just(1, 2, 3)
                .lift(new ThreadContextOperator<Integer>())
                .observeOn(Schedulers.computation())
                .lift(new ThreadContextOperator<ThreadContext>())
                .map((i) -> i)
                .lift(new ThreadContextOperator<ThreadContext>())
                .subscribeOn(Schedulers.newThread())
                .toBlocking()
                .forEach(System.out::println);
    }

    public static class ThreadContext<T> {
        private final List<String> threadName = new ArrayList<>();
        private final T value;

        public ThreadContext(String threadName, T value) {
            this.threadName.add(threadName);
            this.value = value;
        }

        public ThreadContext(List<String> threadName, T value) {
            this.threadName.addAll(threadName);
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public ThreadContext<T> addThread(String name) {
            ArrayList<String> copy = new ArrayList<>(threadName);
            copy.add(name);
            return new ThreadContext<T>(copy, value);
        }

        @Override
        public String toString() {
            return "threadName='" + String.join(" -> ", threadName) + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    private static class ThreadContextOperator<T> implements Observable.Operator<ThreadContext<T>, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super ThreadContext<T>> subscriber) {
            return new Subscriber<T>(subscriber) {
                @Override
                public void onCompleted() {
                    subscriber.onCompleted();
                }

                @Override
                public void onError(Throwable e) {
                    subscriber.onError(e);
                }

                @Override
                public void onNext(T t) {
                    if (t instanceof ThreadContext) {
                        subscriber.onNext(((ThreadContext<T>) t).addThread(Thread.currentThread().getName()));
                    } else {
                        subscriber.onNext(new ThreadContext<T>(Thread.currentThread().getName(), t));
                    }
                }
            };
        }
    }
}
