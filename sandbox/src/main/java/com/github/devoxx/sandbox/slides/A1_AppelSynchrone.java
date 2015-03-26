package com.github.devoxx.sandbox.slides;

import java.util.List;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ServerApi;


public class A1_AppelSynchrone {

    public static void main(String[] args) {
        ServerApi api = new ApiFactory().synchrone();

        List<Movie> movies = api.movies();
        for (Movie movie : movies) {
            Movie translation = api.translation(movie.id, "FR");
            List<Actor> actors = api.actors(movie.id);
            Synopsis synopsis = api.synopsis(movie.id);

            Page page = new Page(translation, synopsis, actors);
            System.out.println(page);
        }
    }
}
