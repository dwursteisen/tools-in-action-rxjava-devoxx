package com.github.devoxx.sandbox.panic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;

import com.github.devoxx.sandbox.twitter.AnswerMachine;
import com.github.devoxx.sandbox.twitter.TwitterFun;
import rx.Observable;
import twitter4j.Status;

import static java.lang.String.format;

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

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Observable<Status> rxJavaStream = TwitterFun.stream().track("RxJava,#DV15TEST").share();

        rxJavaStream
                .map(status -> format("%15s|%s",
                        status.getUser().getScreenName(),
                        status.getText().replaceAll("\n", format("\n%15s|", ""))))
                .forEach(System.out::println, TwitterFun::onError);

        AnswerMachine.observe(rxJavaStream)
                .map(status -> "Replied : " + status.getText() + " (at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
                        + ")")
                .subscribe(System.out::println, System.err::println, latch::countDown);
        latch.await();
        System.out.println("Completed !");
    }

}
