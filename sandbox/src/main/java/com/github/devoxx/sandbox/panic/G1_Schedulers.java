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

// B, D code si nécessaire
public class G1_Schedulers {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        CSV csv = new CSV().open();
        api.movies()
                .flatMapIterable(movies -> movies)
                .flatMap((m) -> api.translation(m.id, "FR"))
                .flatMap((trad) -> composeMovie(api, trad))
                .doOnEach(Tools::threadInfo)
                .doOnUnsubscribe(() -> Tools.printFile(csv.location))
                .forEach(csv::append, Throwable::printStackTrace, csv::close);
        // fails because 'close' is called in a thread source thread pool that is interrupted
    }


    public static Observable<Page> composeMovie(ObservableServerApi api, Movie trad) {
        Observable<List<Actor>> movieActors = api.actors(trad.id);
        Observable<Synopsis> movieSynopsis = api.synopsis(trad.id);
        return Observable.zip(movieActors, movieSynopsis, (actors, synopsis) -> new Page(trad, synopsis, actors));
    }
}
