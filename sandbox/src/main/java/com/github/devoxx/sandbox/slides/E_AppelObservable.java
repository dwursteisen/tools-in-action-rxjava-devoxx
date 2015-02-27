package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

public class E_AppelObservable {

    public static void main(String[] args) {
        ObservableServerApi api = new ApiFactory().observable();

        api.movies().flatMap(Observable::from)
                .flatMap((m) -> api.translation(m.id, "FR"))
                .flatMap((trad) ->
                        api.actors(trad.id).zipWith(api.synopsis(trad.id), ((actors, synopsis) -> {
                            return new Page(trad, synopsis, actors);
                        }))).subscribe(System.out::println);

    }

}
