package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.CallbackServerApi;
import rx.Observable;

// B parle et D code en parlant avec B.
public class C2_CreationObservable {

    private static final CallbackServerApi API = new ApiFactory().callback();

    public static void main(String[] args) {
        Observable<Object> obs = Observable.create(subscriber -> {


        });

        obs.subscribe(System.out::println);

    }
}
