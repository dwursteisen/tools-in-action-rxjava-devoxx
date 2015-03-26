package com.github.devoxx.sandbox.slides;

import java.util.List;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

public class A5_AppelObservable {

    public static void main(String[] args) {
        ObservableServerApi api = new ApiFactory().observable();

        api.movies().flatMap(Observable::from)
                .flatMap((m) -> api.translation(m.id, "FR"))
                .flatMap((trad) -> composeMovie(api, trad))
                .subscribe(System.out::println);

    }

    public static Observable<Page> composeMovie(ObservableServerApi api, Movie trad) {
        Observable<List<Actor>> movieActors = api.actors(trad.id);
        Observable<Synopsis> movieSynopsis = api.synopsis(trad.id);
        return Observable.zip(movieActors, movieSynopsis, (actors, synopsis) -> new Page(trad, synopsis, actors));
    }

}
