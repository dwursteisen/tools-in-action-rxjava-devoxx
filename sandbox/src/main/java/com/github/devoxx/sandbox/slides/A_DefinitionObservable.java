package com.github.devoxx.sandbox.slides;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by david.wursteisen on 26/02/2015.
 */
public class A_DefinitionObservable {

    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> numbers = Observable.just(1, 2, 3, 4);
        numbers.subscribe(System.out::println);

        Observable.interval(1, TimeUnit.SECONDS).subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(5);
    }
}
