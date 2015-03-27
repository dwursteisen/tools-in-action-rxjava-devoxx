package com.github.devoxx.sandbox.panic;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import org.reactivestreams.Publisher;
import reactor.rx.Stream;
import reactor.rx.Streams;
import rx.Observable;
import rx.RxReactiveStreams;

// conclusion : D
public class I_ReactiveStreams {

    public static void main(String[] args) throws InterruptedException {
        Stream<Integer> ints = Streams.range(1, 10);

        RxReactiveStreams.toObservable(ints).map(i -> i + 1).forEach(System.out::println);

        Observable<Movie> movies = new ApiFactory().observable().movies().flatMap(Observable::from);
        Publisher<Movie> rxStreamMovies = RxReactiveStreams.toPublisher(movies);

        Streams.defer(() -> rxStreamMovies)
                .consume(System.out::println, System.err::println);

    }
}
