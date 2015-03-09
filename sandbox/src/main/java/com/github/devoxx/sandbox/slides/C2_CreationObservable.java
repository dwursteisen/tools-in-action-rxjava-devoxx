package com.github.devoxx.sandbox.slides;

import java.util.List;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.CallbackServerApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;

public class C2_CreationObservable {

    public static void main(String[] args) {

        Observable<List<Movie>> asyncCall = Observable.create((subscriber -> {
            CallbackServerApi api = new ApiFactory().callback();
            api.movies(new Callback<List<Movie>>() {
                @Override
                public void success(List<Movie> movies, Response response) {
                    subscriber.onNext(movies);
                    subscriber.onCompleted();
                }

                @Override
                public void failure(RetrofitError error) {
                    subscriber.onError(error);
                }
            });
        }));

        asyncCall.subscribe(System.out::println);
    }
}
