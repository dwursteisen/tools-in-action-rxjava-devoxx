package com.github.devoxx.sandbox;

import java.util.Optional;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ServerApi;
import retrofit.RestAdapter;
import rx.Observable;

/**
 _____             _
 |  __ \           | |
 | |__) |__  __    | |  __ _ __   __ __ _
 |  _  / \ \/ /_   | | / _` |\ \ / // _` |
 | | \ \  >  <| |__| || (_| | \ V /| (_| |
 |_|  \_\/_/\_\\____/  \__,_|  \_/  \__,_|

 +--------------------------------------+
 |     From Future To RxJava            |
 +--------------------------------------+

 Brice Dutheil      -    David Wursteisen

 twitter : @BriceDutheil
 twitter : @dwursteisen

 */

public class Main {
    public static void main(String[] args) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build();

        ServerApi api = adapter.create(ServerApi.class);
        api.movies().flatMap(Observable::from)
                .flatMap((m) -> fromMovie(api, m))
                .toBlocking().forEach(System.out::println);
    }

    private static Observable<String> fromMovie(ServerApi api, Movie m) {
        return api.actors(m.title).zipWith(api.synopsis(m.title), ((actors, synopsis) -> String.format(
                "------ %s -------\n" +
                        "--> Actors : %s\n" +
                        "%s\n",

                m.title,
                actors,
                Optional.ofNullable(synopsis).map((s) -> s.synopsis).orElse(""))));
    }
}
