package com.github.devoxx.sandbox.panic;

import java.util.List;
import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.CSV;
import com.github.devoxx.sandbox.tooling.Tools;
import rx.Observable;
import rx.schedulers.Schedulers;

// B, D code si nécessaire
public class G2_Schedulers {

    public static void main(String[] args) {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        CSV csv = new CSV().open();
        api.movies()
                .flatMapIterable(movies -> movies)
                .flatMap((m) -> api.translation(m.id, "FR"))
                .flatMap((trad) -> composeMovie(api, trad))
                .doOnEach(Tools::threadInfo)
                .observeOn(Schedulers.io())
                .doOnUnsubscribe(() -> Tools.printFile(csv.location))
                .forEach(csv::append, System.err::println, csv::close);
    }


    public static Observable<Page> composeMovie(ObservableServerApi api, Movie trad) {
        Observable<List<Actor>> movieActors = api.actors(trad.id);
        Observable<Synopsis> movieSynopsis = api.synopsis(trad.id);
        return Observable.zip(movieActors, movieSynopsis, (actors, synopsis) -> new Page(trad, synopsis, actors));
    }
}
