package com.github.devoxx.sandbox.panic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import rx.Observable;

/**
 * <pre>
 *  ____           _
 * |  _ \ __  __  | | __ ___   ____ _
 * | |_) |\ \/ /  | |/ _` \ \ / / _` |
 * |  _ <  >  < |_| | (_| |\ V / (_| |
 * |_| \_\/_/\_\___/ \__,_| \_/ \__,_|
 *
 * +--------------------------------------------------------------+
 * | http://reactivex.io/                              @ReactiveX |
 * | https://github.com/ReactiveX/RxJava                  @RxJava |
 * |                                                              |
 * | https://github.com/dwursteisen/tools-in-action-rxjava-devoxx |
 * +--------------------------------------------------------------+
 *
 * twitter : @BriceDutheil
 * twitter : @dwursteisen
 *
 * </pre>
 */
public class Z_Outro {
    public static void main(String[] args) {
        Future<String> future = EXECUTOR_SERVICE.submit(() -> "Hello Devoxx France !");
        Observable.from(future).subscribe(System.out::println);
    }

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
}
