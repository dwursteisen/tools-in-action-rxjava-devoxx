package com.github.devoxx.sandbox.panic;

import java.util.List;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;
import rx.schedulers.Schedulers;

// B parle ?
public class A5_AppelObservable {

    public static void main(String[] args) {
        ObservableServerApi api = new ApiFactory().unreliablePartner().observable();

        api.movies()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap((trad) -> {
                    Observable<List<Actor>> movieActors = api.actors(trad.id);
                    Observable<Synopsis> movieSynopsis = api.synopsis(trad.id);
                    return Observable.zip(movieActors, movieSynopsis, (actors, synopsis) -> new Page(trad, synopsis, actors));
                })
                .toBlocking()
                .forEach(System.out::println);


    }



}
