package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.observable.ObservableExample;
import rx.Observable;

/**
 * Created by david.wursteisen on 09/03/2015.
 */
public class E_Composition {

    public static void main(String[] args) {
        Observable<String> keys = ObservableExample.fromKeyboard();
        Observable<String> time = ObservableExample.fromTime();

        Observable.merge(time, keys).toBlocking().forEach(E_Composition::display);
        Observable.combineLatest(time, keys, (str1, str2) -> str1 + " --> " + str2).toBlocking().forEach(E_Composition::display);
        Observable.zip(time, keys, (t, c) -> t + "/" + c).toBlocking().forEach(E_Composition::display);

    }

    public static void display(String str) {
        System.err.println("~> " + str);
    }

}
