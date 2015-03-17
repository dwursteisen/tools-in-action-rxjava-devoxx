package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.client.Clients;
import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.schedulers.Schedulers;

public class H0_BackPressure {

    /**
     * Max-concurrency on the flatMap needed to raise the MissingBackpressureException
     * Otherwise the flatMap is "unbouded", this number should probably be the same as the thread pool size configured for retrofit
     */
    public static final int MAX_CONCURRENT_CALLS = 4;

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().unreliablePartner().observable();

        Clients.sending_lot_of_refresh_queries()
//                .onBackpressureDrop() // without this it will receive a MissingBackpressureException
                .observeOn(Schedulers.io())
                .flatMap(q -> api.movies(), MAX_CONCURRENT_CALLS)
                .flatMapIterable(movies -> movies)
//                .map(H0_BackPressure::calculatePopularlyRatings)
                .toBlocking().forEach(System.out::println);
    }

    private static Movie calculatePopularlyRatings(Movie movie) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) { }
        return movie;
    }
}
