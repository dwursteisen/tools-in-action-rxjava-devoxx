package com.github.devoxx.sandbox.retrofit;

import java.util.List;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Synopsis;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ServerApi {

    @GET("/movies")
    List<Movie> movies();

    @GET("/movies/cast/{id}")
    List<Actor> actors(@Path("id") String id);

    @GET("/movies/synopsis/{id}")
    Synopsis synopsis(@Path("id") String id);

    @GET("/movies/translation/{id}/{lang}")
    Movie translation(@Path("id") String id, @Path("lang") String lang);
}
