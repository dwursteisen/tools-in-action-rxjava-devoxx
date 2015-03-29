package com.github.devoxx.sandbox.panic;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

// suite directe de F2 => doit etre mergé dans F2
public class F3_ErrorHandling {

    public static void main(String[] args) {


        ObservableServerApi api = new ApiFactory().observable();

        Observable.just("thedarkknight", "the2godfather", "pulpfiction", "fightclub")
                .concatMap((id) -> api.traduction(id, "FR").onExceptionResumeNext(Observable.empty()))
                .toBlocking().forEach(System.out::println);
    }
}
