package com.github.devoxx.sandbox.panic;

import static java.lang.String.format;

import com.github.devoxx.sandbox.operators.Throttler;
import com.github.devoxx.sandbox.twitter.AnswerMachine;
import com.github.devoxx.sandbox.twitter.DumbStatus;
import com.github.devoxx.sandbox.twitter.TwitterFun;
import rx.Observable;
import twitter4j.Status;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    public static final DumbStatus TWEET_1 = new DumbStatus("dwursteisen", "#DVFR15 @RxJava tia fail do not exists rubish = -< ][\\ ] ");
    public static final DumbStatus TWEET_2 = new DumbStatus("dwursteisen", "#DVFR15 @RxJava Fight Club");
    public static final DumbStatus TWEET_3 = new DumbStatus("dwursteisen", "#DVFR15 @RxJava The Dark Knight");

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
       Observable<Status> rxJavaStream = TwitterFun.stream().track("RxJava,#DV15TEST").share();

        rxJavaStream
                .map(status -> format("%15s|%s",
                        status.getUser().getScreenName(),
                        status.getText().replaceAll("\n", format("\n%15s|", ""))))
                        .forEach(System.out::println, TwitterFun::onError);

        // Observable<Status> mock = Observable.just(TWEET_1, TWEET_2, TWEET_3);

        //AnswerMachine.observe(mock)
                AnswerMachine.observe(rxJavaStream) // uncomment for real tweets
                .map(status -> "Replied : " + status.getText())
                .subscribe(System.out::println, System.err::println, latch::countDown);
        latch.await();
        System.out.println("Completed !");
    }

}
