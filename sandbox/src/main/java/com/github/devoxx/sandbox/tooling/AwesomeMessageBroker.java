package com.github.devoxx.sandbox.tooling;

import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import com.github.devoxx.sandbox.model.Movie;

public class AwesomeMessageBroker {

    public static AwesomeMessageBroker aeron() {
        return new AwesomeMessageBroker();
    }

    public static AwesomeMessageBroker kafka() {
        return new AwesomeMessageBroker();
    }

    public static AwesomeMessageBroker zeroMQ() {
        return new AwesomeMessageBroker();
    }

    public static AwesomeMessageBroker definatelyNotJMS() {
        return new AwesomeMessageBroker();
    }

    public Publisher<String> moviesRefreshCommands() {
        return subscriber -> {
            for (int i = 0; i < 5; i++) {
                subscriber.onNext("refresh-command-" + i);
            }
            subscriber.onComplete();
        };
    }

    public void forwardMoviesToDataMining(Publisher<Movie> moviePublisher) {
        moviePublisher.subscribe(new Subscriber<Movie>() {
            private Subscription subscription;
            private AtomicLong counter = new AtomicLong(0);

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                counter.set(0);
                this.subscription.request(1);
            }

            @Override
            public void onNext(Movie movie) {
                subscription.request(1);
                counter.incrementAndGet();
                System.out.printf("Launched face recognition on '%s'%n", movie.title);
            }

            @Override
            public void onError(Throwable throwable) {
                subscription.cancel();
                throwable.printStackTrace(System.err);
            }

            @Override
            public void onComplete() {
                System.out.printf("Datamining will process %d movies in this batch%n", counter.get());
            }
        });
    }
}
