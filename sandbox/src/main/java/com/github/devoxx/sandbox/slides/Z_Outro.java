package com.github.devoxx.sandbox.slides;

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
 *  ________     ___    ___  ___  ________  ___      ___ ________
 * |\   __  \   |\  \  /  /||\  \|\   __  \|\  \    /  /|\   __  \
 * \ \  \|\  \  \ \  \/  / /\ \  \ \  \|\  \ \  \  /  / | \  \|\  \
 *  \ \   _  _\  \ \    / /_ \ \  \ \   __  \ \  \/  / / \ \   __  \
 *   \ \  \\  \|  /     \/\  \\_\  \ \  \ \  \ \    / /   \ \  \ \  \
 *    \ \__\\ _\ /  /\   \ \________\ \__\ \__\ \__/ /     \ \__\ \__\
 *     \|__|\|__/__/ /\ __\|________|\|__|\|__|\|__|/       \|__|\|__|
 *              |__|/ \|__|
 *
 * +-----------------------------------------------------------------+
 * | http://reactivex.io/                                 @ReactiveX |
 * | https://github.com/ReactiveX/RxJava                     @RxJava |
 * |                                                                 |
 * | https://github.com/dwursteisen/tools-in-action-rxjava-devoxx    |
 * | http://rxmarbles.com/                                           |
 * +-----------------------------------------------------------------+
 *
 * twitter : @BriceDutheil
 * twitter : @dwursteisen
 *
 * </pre>
 */
public class Z_Outro {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Observable<Status> rxJavaStream = TwitterFun.stream().track("RxJava", "#DV15TEST", "#DV15", "#DVXFR15", "#MOVIE").share();

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
