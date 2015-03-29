package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.observable.ObservableExample;
import rx.Observable;

import java.util.concurrent.TimeUnit;

// B parle. D code montre javadoc (Ctrl+Q)
public class E1_Composition {

    // use display !
    // merge  -> combineLatest -> zip
    public static void main(String[] args) throws InterruptedException {
        Observable<String> keys = ObservableExample.fromKeyboard();
        Observable<String> time = ObservableExample.fromTime(1, TimeUnit.SECONDS);


        TimeUnit.MINUTES.sleep(1);
    }

    public static void display(String str) {
        System.err.println("~> " + str);
    }

}
