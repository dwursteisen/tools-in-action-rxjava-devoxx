package com.github.devoxx.sandbox.slides;

import java.util.List;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.CallbackServerApi;
import com.github.devoxx.sandbox.retrofit.ServerApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;

public class F_CreationObservable {

    public static void main(String[] args) {
        Observable<List<Movie>> synCall = Observable.create((subscriber -> {
            ServerApi api = new ApiFactory().synchrone();
            List<Movie> movies = api.movies();
            subscriber.onNext(movies);
            subscriber.onCompleted();
        }));
        synCall.flatMap(Observable::from).subscribe(System.out::println);

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

        asyncCall.flatMap(Observable::from).subscribe(System.out::println);
    }
}
