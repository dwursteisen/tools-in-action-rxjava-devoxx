package com.github.devoxx.sandbox.panic;

import java.util.concurrent.TimeUnit;

import rx.Observable;

// D parle et codé
public class B_DefinitionObservable {

    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> numbers = Observable.just(1, 2, 3, 4);
        numbers.subscribe(System.out::println);

        Observable.interval(1, TimeUnit.SECONDS).subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(5);
    }
}
