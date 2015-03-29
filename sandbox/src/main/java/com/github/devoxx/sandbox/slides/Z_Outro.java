package com.github.devoxx.sandbox.slides;

import static java.util.concurrent.TimeUnit.SECONDS;
import rx.schedulers.Schedulers;

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
        Schedulers.immediate().createWorker()
                .schedule(() -> System.out.println("RxJava is awesome"), 5, SECONDS);
    }
}
