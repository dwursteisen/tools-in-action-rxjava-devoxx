package com.github.devoxx.sandbox.slides;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by david.wursteisen on 11/03/2015.
 */
// B, D code si nécessaire
public class G1_Schedulers {

    public static void main(String[] args) {
        Observable.create(subscriber -> {
            System.out.println("Sub : " + Thread.currentThread().getName());
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
            subscriber.onNext(4);
            subscriber.onCompleted();
        })
                .doOnNext((v) -> System.out.println("Name1 : " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .map(i -> i)
                .doOnNext((v) -> System.out.println("Name2 : " + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .map(i -> i)
                .doOnNext((v) -> System.out.println("Name3 : " + Thread.currentThread().getName()))
                .toBlocking().forEach(System.err::println);
    }
}
