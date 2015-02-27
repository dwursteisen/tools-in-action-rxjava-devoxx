package com.github.devoxx.sandbox.slides;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ServerApi;

public class C_AppelAsynchrone {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ServerApi api = new ApiFactory().synchrone();

        Future<List<Movie>> movies = threadPool.submit(api::movies);

        for (Movie movie : movies.get()) {
            Future<Movie> translation = threadPool.submit(() -> api.translation(movie.id, "FR"));
            Future<List<Actor>> actors = threadPool.submit(() -> api.actors(movie.id));
            Future<Synopsis> synopsis = threadPool.submit(() -> api.synopsis(movie.id));

            Page page = new Page(translation.get(), synopsis.get(), actors.get());
            System.out.println(page);
        }
    }
}
