package com.github.devoxx.sandbox.panic;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.AwesomeMessageBroker;
import org.reactivestreams.Publisher;
import rx.Observable;
import rx.RxReactiveStreams;

// conclusion : D
public class I_ReactiveStreams {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        Publisher<String> publisher = AwesomeMessageBroker.aeron().moviesRefreshCommands();

        Observable<Movie> movies = RxReactiveStreams
                .toObservable(publisher)
                .flatMap(refreshCommand -> api.movies())
                .flatMapIterable(mi -> mi);

        Publisher<Movie> toPublish = RxReactiveStreams.toPublisher(movies.share());
        AwesomeMessageBroker.kafka()
                .forwardMoviesToDataMining(toPublish);
    }

}
