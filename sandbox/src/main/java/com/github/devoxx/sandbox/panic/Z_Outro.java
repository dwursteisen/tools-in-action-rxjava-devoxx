package com.github.devoxx.sandbox.panic;

import static java.lang.String.format;
import com.github.devoxx.sandbox.twitter.TwitterFun;

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
        Observable<Status> rxJavaStream = TwitterFun.stream().track("RxJava").share();

        rxJavaStream
                .map(status -> format("%15s|%s",
                        status.getUser().getScreenName(),
                        status.getText().replaceAll("\n", format("\n%15s|", ""))))
                .forEach(System.out::println, TwitterFun::onError);
    }
}
