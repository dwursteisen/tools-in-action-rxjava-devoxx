package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.observable.ObservableExample;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class E3_Composition {

    public static void main(String[] args) {
        Observable<String> keys = ObservableExample.fromKeyboard();
        Observable<String> time = ObservableExample.fromTime(1, TimeUnit.SECONDS);

        Observable.zip(time, keys, (t, c) -> t + "/" + c).toBlocking().forEach(E3_Composition::display);

    }

    public static void display(String str) {
        System.err.println("~> " + str);
    }

}
