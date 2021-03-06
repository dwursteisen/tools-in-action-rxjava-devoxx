package com.github.devoxx.sandbox.panic;

import java.util.concurrent.TimeUnit;

import com.github.devoxx.sandbox.observable.ObservableExample;
import rx.Observable;

// B parle. D code après et essaye de montrer le schéma de la javadoc (Ctrl+Q)
public class E2_Composition {

    public static void main(String[] args) {
        Observable<String> keys = ObservableExample.fromKeyboard();
        Observable<String> time = ObservableExample.fromTime(1, TimeUnit.SECONDS);

        // zip permet de composer un résultat
        Observable.zip(time, keys, (t, c) -> t + "/" + c).toBlocking().forEach(E2_Composition::display);

    }

    public static void display(String str) {
        System.err.println("~> " + str);
    }

}
