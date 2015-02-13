package com.github.devoxx.sandbox.retrofit;

import java.util.List;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Synopsis;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by david on 12/02/15.
 */
public interface ServerApi {

    @GET("/movies")
    Observable<List<Movie>> movies();

    @GET("/movies/cast/{id}")
    Observable<List<Actor>> actors(@Path("id") String movieId);

    @GET("/movies/synopsis/{id}")
    Observable<Synopsis> synopsis(@Path("id") String title);
}
