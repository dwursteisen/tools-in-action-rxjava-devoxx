package com.github.devoxx.sandbox.retrofit;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

import java.util.List;

/**
 * Created by david on 12/02/15.
 */
public interface ServerApi {

    @GET("/movies")
    Observable<List<Movie>> movies();

    @GET("/movies/{id}")
    Observable<List<Actor>> actors(@Path("id") String movieId);
}
