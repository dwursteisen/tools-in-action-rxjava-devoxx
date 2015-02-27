package com.github.devoxx.sandbox.retrofit;

import java.util.List;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Synopsis;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by david on 12/02/15.
 */
public interface CallbackServerApi {

    @GET("/movies")
    void movies(Callback<List<Movie>> callback);

    @GET("/movies/cast/{id}")
    void actors(@Path("id") String id, Callback<List<Actor>> callback);

    @GET("/movies/synopsis/{id}")
    void synopsis(@Path("id") String id, Callback<Synopsis> callback);

    @GET("/movies/translation/{id}/{lang}")
    void translation(@Path("id") String id, @Path("lang") String lang, Callback<Movie> callback);
}
