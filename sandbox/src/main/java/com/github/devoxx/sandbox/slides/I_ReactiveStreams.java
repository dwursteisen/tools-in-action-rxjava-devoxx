package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.AwesomeMessageBroker;
import org.reactivestreams.Publisher;

// conclusion : D
public class I_ReactiveStreams {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        Publisher<String> publisher = AwesomeMessageBroker.aeron().moviesRefreshCommands();
        // moviesRefresh -> toObservable -> flatMap movies -> flatMap list -> forward

        // Consume in AwesomeMessageBroker

    }
}
