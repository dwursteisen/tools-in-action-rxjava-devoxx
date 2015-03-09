package com.github.devoxx.sandbox.observable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by david.wursteisen on 09/03/2015.
 */
public class ObservableExample {

    public static Observable<String> fromNames() {
        return Observable.just("Bob", "Mary", "Cedric", "Patrick", "Olivia");
    }

    public static Observable<String> fromTime() {
        return Observable.interval(3, TimeUnit.SECONDS)
                .map(t -> LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

    }

    public static Observable<String> fromKeyboard() {
        Observable<String> keys = Observable.create(subscriber -> {

            Scanner scan = new Scanner(System.in);
            String line = scan.next();
            while (!"END".equalsIgnoreCase(line)) {
                subscriber.onNext(line);
                line = scan.next();
            }
            subscriber.onCompleted();

        });
        return keys.subscribeOn(Schedulers.newThread());
    }
}
