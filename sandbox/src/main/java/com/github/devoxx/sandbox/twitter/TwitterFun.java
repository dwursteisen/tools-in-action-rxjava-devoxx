package com.github.devoxx.sandbox.twitter;

import java.util.Arrays;
import rx.Observable;
import rx.subjects.PublishSubject;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public abstract class TwitterFun {

    public static void onError(Throwable throwable) {
        System.out.println();
        System.out.println("                     ¯\\_(ツ)_/¯");
        System.out.println();
        System.out.println(
                "┊┊┊┊┊╭━┫┊DOWN ?┊\n" +
                "┊┊╭━━━╮╰━╭━━━╮━━\n" +
                "┊┊┃┈▋▋┃┊┊┃▋▋┈┃┊┊\n" +
                "┏━╯┈┈┈◣┊┊◢┈┈┈╰━┓\n" +
                "┃┗━╯┈┈┃┊┊┃┈┈╰━┛┃\n" +
                "╰━┳━┳━╯┊┊╰━┳━┳━╯\n" +
                "━━┻━┻━━━━━━┻━┻━━\n" +
                "┈╱▔▔▔▔▔▔▔╲┏━╮╭━┓\n" +
                "▕┈╭╮┈┈┈┈┈▕╰━╮╭━╯\n" +
                "▕━━━╯┈┈┈┈┈╲━╯┃┈┈\n" +
                "┈╲▂▂▂▂▂▂▂▂▂▂▂╯┈┈\n" +
                "┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈\n" +
                "╭┳╭┳╭┳╭┳╭┳╭┳╭┳╭┳\n" +
                "╯╰╯╰╯╰╯╰╯╰╯╰╯╰╯╰ "
        );
        System.out.println();
        System.out.println(throwable.toString());
    }



    public static TimelineMesserTwitterFun client() {
        PublishSubject<Status> twitterObservable = PublishSubject.create();

        Twitter twitter = new TwitterFactory().getInstance();
        return new TimelineMesserTwitterFun(twitter);
    }


    public static class TimelineMesserTwitterFun extends TwitterFun {
        private Twitter twitter;

        public TimelineMesserTwitterFun(Twitter twitter) {
            this.twitter = twitter;
        }

        public Observable<Status> updateStatus(String statusText) {
            System.out.println("updateStatus : " + statusText);
            StatusUpdate latestStatus = new StatusUpdate(statusText);
            return Observable.create(subscriber -> {
                try {
                    subscriber.onNext(twitter.updateStatus(latestStatus));
                } catch (TwitterException te) {
                    subscriber.onError(te);
                } finally {
                    subscriber.onCompleted();
                }
            });
        }
    }


    public static StreamingTwitterFun stream() {
        PublishSubject<Status> twitterObservable = PublishSubject.create();

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                twitterObservable.onNext(status);
            }

            @Override
            public void onException(Exception ex) {
                twitterObservable.onError(ex);
            }
        });
        return new StreamingTwitterFun(
                twitterObservable.doOnUnsubscribe(twitterStream::cleanUp),
                twitterStream);
    }

    public static class StreamingTwitterFun extends TwitterFun {
        private final TwitterStream twitterStream;
        private Observable<Status> twitterObservable;

        public StreamingTwitterFun(Observable<Status> statusObservable, TwitterStream twitterStream) {
            this.twitterObservable = statusObservable;
            this.twitterStream = twitterStream;
        }

        public Observable<Status> sample() {
            return twitterObservable
                    .doOnSubscribe(twitterStream::sample)
                    .doOnSubscribe(() -> System.out.println("Tweets stream sampling started"))
                    ;
        }

        public Observable<Status> track(String... singleWords) {
            FilterQuery query = new FilterQuery();
            query.track(singleWords);
            return twitterObservable
                    .doOnSubscribe(() -> twitterStream.filter(query))
                    .doOnSubscribe(() -> System.out.printf("Looking for tweets with : %s%n", Arrays.deepToString(singleWords)))
                    ;
        }
    }
}
