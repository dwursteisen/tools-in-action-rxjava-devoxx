package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.observable.ObservableExample;
import rx.Observable;

import java.util.concurrent.TimeUnit;

// B parle. D code après et essaye de montrer le schéma de la javadoc (Ctrl+Q)
public class E1_Composition {

    public static void main(String[] args) {
        Observable<String> keys = ObservableExample.fromKeyboard();
        Observable<String> time = ObservableExample.fromTime(1, TimeUnit.SECONDS);

        Observable.merge(time, keys).toBlocking().forEach(E1_Composition::display);

    }

    public static void display(String str) {
        System.err.println("~> " + str);
    }

}
