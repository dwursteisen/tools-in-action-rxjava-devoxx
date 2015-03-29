package com.github.devoxx.sandbox.slides;

import java.util.List;
import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

// B, D code si nécessaire
public class G1_Schedulers {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        // on each print thread info
        // open CSV -> subscribe and append, print err, close csv

        api.movies()
                .flatMapIterable(movies -> movies)
                .flatMap((m) -> api.translation(m.id, "FR"))
                .flatMap((trad) -> composeMovie(api, trad))
        ;
    }


    public static Observable<Page> composeMovie(ObservableServerApi api, Movie trad) {
        Observable<List<Actor>> movieActors = api.actors(trad.id);
        Observable<Synopsis> movieSynopsis = api.synopsis(trad.id);
        return Observable.zip(movieActors, movieSynopsis, (actors, synopsis) -> new Page(trad, synopsis, actors));
    }
}
