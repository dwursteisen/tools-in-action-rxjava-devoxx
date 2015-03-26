package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.model.Movie;
import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import rx.Observable;

/**
 * Created by david.wursteisen on 11/03/2015.
 */
public class F2_ErrorHandling {

    public static void main(String[] args) {


        ObservableServerApi api = new ApiFactory().observable();

        Observable.just("thedarkknight", "the2godfather", "pulpfiction", "fightclub")
                .concatMap((id) -> api.traduction(id, "FR").onExceptionResumeNext(Observable.just(new Movie("oup"))))
                .toBlocking().forEach(System.out::println);


        /*
        Observable<Integer> stream = Observable.range(1, 100)
                .flatMap((i) ->
                        Observable.defer(() -> Observable.just(throwExceptionIf3(i)))
                                .onErrorReturn(z -> 666)
                );

        stream.subscribe(System.out::println, Throwable::printStackTrace);

        stream.onErrorResumeNext(Observable.never()).subscribe(System.out::println, Throwable::printStackTrace);

        stream.retry(2).subscribe(System.out::println, Throwable::printStackTrace);
*/
    }

    private static Integer throwExceptionIf3(Integer i) {
        if (i == 3) {
            throw new RuntimeException("* Oups on value ~> " + i);
        }
        return i;
    }
}
