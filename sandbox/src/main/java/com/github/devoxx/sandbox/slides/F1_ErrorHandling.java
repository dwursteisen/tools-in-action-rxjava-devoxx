package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

// B parle. D reprend la main après le code
public class F1_ErrorHandling {

    public static void main(String[] args) {


        ObservableServerApi api = new ApiFactory().observable();

        Observable.just("thedarkknight", "the2godfather", "pulpfiction", "fightclub")
                .toBlocking().forEach(System.out::println);



    }

}
