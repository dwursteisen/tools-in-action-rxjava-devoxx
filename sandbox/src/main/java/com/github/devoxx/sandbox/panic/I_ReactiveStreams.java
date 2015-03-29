package com.github.devoxx.sandbox.panic;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.AwesomeMessageBroker;
import rx.Observable;
import rx.RxReactiveStreams;

// conclusion : D
public class I_ReactiveStreams {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        Observable<Movie> movies = RxReactiveStreams
                .toObservable(AwesomeMessageBroker.aeron().moviesRefreshCommands())
                .flatMap(refreshCommand -> api.movies())
                .flatMapIterable(mi -> mi);

        AwesomeMessageBroker.kafka()
                .forwardMoviesToDataMining(RxReactiveStreams.toPublisher(movies.share()));
    }

}
