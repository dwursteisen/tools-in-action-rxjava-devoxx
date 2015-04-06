package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.operators.TimeRateLimiter;
import rx.Observable;

import java.util.concurrent.TimeUnit;


// D parle + code
public class H1_BackPressure {

    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1000)
                .lift(new TimeRateLimiter<>(1, TimeUnit.SECONDS))
                .toBlocking()
                .forEach(System.out::println);

    }

}
