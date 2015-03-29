package com.github.devoxx.sandbox.panic;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

// D code + parle
public class F2_ErrorHandling {

    public static void main(String[] args) {


        ObservableServerApi api = new ApiFactory().observable();

        Observable.just("thedarkknight", "the2godfather", "pulpfiction", "fightclub")
                .concatMap((id) -> api.traduction(id, "FR").onExceptionResumeNext(Observable.just(new Movie("oup"))))
                .toBlocking().forEach(System.out::println);
    }
}
