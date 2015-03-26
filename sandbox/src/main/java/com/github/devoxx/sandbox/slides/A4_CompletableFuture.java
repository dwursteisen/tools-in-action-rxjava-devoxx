package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.model.MutablePage;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ServerApi;

import static java.util.concurrent.CompletableFuture.supplyAsync;

// B parle
public class A4_CompletableFuture {
    public static void main(String[] args) {

        ServerApi api = new ApiFactory().synchrone();

        supplyAsync(api::movies)
                .thenAccept(movies ->
                        movies.stream()
                                .map(movie ->
                                        supplyAsync(() -> api.translation(movie.id, "FR"))
                                                .thenCombineAsync(
                                                        supplyAsync(() -> api.actors(movie.id)),
                                                        (movie1, actors) -> {
                                                            MutablePage mutablePage = new MutablePage();
                                                            mutablePage.setMovie(movie1);
                                                            mutablePage.setActors(actors);
                                                            return mutablePage;
                                                        })
                                                .thenCombineAsync(
                                                        supplyAsync(() -> api.synopsis(movie.id)),
                                                        (mutablePage1, synopsis) -> {
                                                            mutablePage1.setSynopsis(synopsis);
                                                            return mutablePage1;
                                                        }))
                                .forEach(fp -> fp.thenAccept(System.out::println).join()))
                .join()
        ;
    }

}
