package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;

// conclusion : D
public class I_ReactiveStreams {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        // moviesRefresh -> toObservable -> flatMap movies -> flatMap list -> forward

        // AwesomeMessageBroker



        // AwesomeMessageBroker
    }
}
