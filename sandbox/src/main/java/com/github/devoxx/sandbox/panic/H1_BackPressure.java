package com.github.devoxx.sandbox.panic;

import java.util.concurrent.TimeUnit;

import com.github.devoxx.sandbox.operators.Throttler;
import rx.Observable;

// D parle + code
public class H1_BackPressure {

    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1000)
                .lift(new Throttler<>(1, TimeUnit.SECONDS))
                .toBlocking()
                .forEach(System.out::println);

    }

}
