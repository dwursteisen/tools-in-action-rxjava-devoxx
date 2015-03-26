package com.github.devoxx.sandbox.slides;

import java.util.List;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ServerApi;
import rx.Observable;

// B parle et D code en parlant avec B.
public class C1_CreationObservable {

    public static void main(String[] args) {
        Observable<List<Movie>> synCall = Observable.create((subscriber -> {
            ServerApi api = new ApiFactory().synchrone();
            List<Movie> movies = api.movies();
            subscriber.onNext(movies);
            subscriber.onCompleted();
        }));
        synCall.subscribe(System.out::println);

    }
}
