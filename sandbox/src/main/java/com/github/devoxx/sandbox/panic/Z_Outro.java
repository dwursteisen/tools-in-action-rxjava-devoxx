package com.github.devoxx.sandbox.panic;

import static java.lang.String.format;
import com.github.devoxx.sandbox.twitter.AnswerMachine;
import com.github.devoxx.sandbox.twitter.TwitterFun;
import rx.Observable;
import twitter4j.Status;


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
    public static void main(String[] args) {
        Observable<Status> rxJavaStream = TwitterFun.stream().track("RxJava,#DVFR15").share();

        rxJavaStream
                .map(status -> format("%15s|%s",
                        status.getUser().getScreenName(),
                        status.getText().replaceAll("\n", format("\n%15s|", ""))))
                .forEach(System.out::println, TwitterFun::onError);

//        AnswerMachine.observe(Observable.just(new DumbStatus("dwursteisen", "#DVFR15 @RxJava tia fail do not exists rubish = -< ][ ] fightclub thedarkknight \"The Godfather\"")))
        AnswerMachine.observe(rxJavaStream)
                .map(status -> "Replied        : " + status.getText())
                .subscribe(System.out::println, System.err::println);
    }
}
