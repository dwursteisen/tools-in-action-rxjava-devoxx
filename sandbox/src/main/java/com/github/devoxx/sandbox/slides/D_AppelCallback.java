package com.github.devoxx.sandbox.slides;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.github.devoxx.sandbox.model.Actor;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.model.Page;
import com.github.devoxx.sandbox.model.Synopsis;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.CallbackServerApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class D_AppelCallback {

    public static void main(String[] args) {
        CallbackServerApi api = new ApiFactory().callback();

        api.movies(new Callback<List<Movie>>() {
            @Override
            public void success(List<Movie> movies, Response response) {
                for (Movie movie : movies) {
                    Page page = new Page();
                    CountDownLatch latchTranslation = new CountDownLatch(1);
                    CountDownLatch latchActors = new CountDownLatch(1);
                    CountDownLatch latchSynopsis = new CountDownLatch(1);

                    api.translation(movie.id, "FR", new Callback<Movie>() {
                        @Override
                        public void success(Movie movie, Response response) {
                            page.movie = movie;
                            latchTranslation.countDown();
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                    api.actors(movie.id, new Callback<List<Actor>>() {
                        @Override
                        public void success(List<Actor> actors, Response response) {
                            page.actors = actors;
                            latchActors.countDown();
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                    api.synopsis(movie.id, new Callback<Synopsis>() {
                        @Override
                        public void success(Synopsis synopsis, Response response) {
                            page.synopsis = synopsis;
                            latchSynopsis.countDown();
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                    try {
                        latchActors.await();
                        latchSynopsis.await();
                        latchTranslation.await();

                        System.out.print(page);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
