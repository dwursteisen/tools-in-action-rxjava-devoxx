package com.github.devoxx.sandbox.slides;

import rx.Observable;

/**
 * Created by david.wursteisen on 11/03/2015.
 */
public class F_ErrorHandling {

    public static void main(String[] args) {
        Observable<Integer> stream = Observable.just(1, 2, 3, 4)
                .concatWith(Observable.error(new RuntimeException("* OUPS *")))
                .concatWith(Observable.just(6, 7, 8));

        stream.subscribe(System.out::println, Throwable::printStackTrace);

        stream.onErrorResumeNext(Observable.just(0)).subscribe(System.out::println, Throwable::printStackTrace);

        stream.retry(2).subscribe(System.out::println, Throwable::printStackTrace);

    }
}
