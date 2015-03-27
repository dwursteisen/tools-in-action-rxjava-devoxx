package com.github.devoxx.sandbox.panic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import rx.Observable;

/**
 * <pre>
 * _____             _
 * |  __ \           | |
 * | |__) |__  __    | |  __ _ __   __ __ _
 * |  _  / \ \/ /_   | | / _` |\ \ / // _` |
 * | | \ \  >  <| |__| || (_| | \ V /| (_| |
 * |_|  \_\/_/\_\\____/  \__,_|  \_/  \__,_|
 *
 * +--------------------------------------+
 * |     From Future To RxJava            |
 * +--------------------------------------+
 *
 * Brice Dutheil      -    David Wursteisen
 *
 * twitter : @BriceDutheil
 * twitter : @dwursteisen
 *
 * </pre>
 */
public class _Presentation {
    public static void main(String[] args) {
        Future<String> future = EXECUTOR_SERVICE.submit(() -> "Hello Devoxx France !");
        Observable.from(future).subscribe(System.out::println);
    }

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
}
