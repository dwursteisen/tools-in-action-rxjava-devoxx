package com.github.devoxx.sandbox.tooling;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import com.github.devoxx.sandbox.model.Page;
import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class Tools {

    private static AtomicLong counter = new AtomicLong(0);
    public static final Scheduler SINGLE_THREADED_SCHEDULER = Schedulers.from(newSingleThreadExecutor(r -> new Thread(r, "single")));
    public static final Scheduler FINISH_SCHEDULER = Schedulers.from(newFixedThreadPool(4, r -> new Thread(r, "finish-pool-" + counter.getAndIncrement())));

    // artificial way to create huge tasks
    public static <T> Observable<T> calculateHeavyStuff(T o) {
        try {
            threadInfo("start", o);
            TimeUnit.SECONDS.sleep(5);
            threadInfo("end", o);
            return Observable.just(o);
        } catch (InterruptedException ie) {
            currentThread().interrupt();
            return Observable.empty();
        }
    }

    public static <I> void threadInfo(I item) {
        threadInfo("", item);
    }

    public static <I> void threadInfo(String source, I item) {
        if (item instanceof Notification) {
            if (!((Notification) item).isOnNext()) {
                return;
            }
            printThreadInfo(source, (I) ((Notification) item).getValue());
        } else {
            printThreadInfo(source, item);
        }
    }

    private static void printThreadInfo(String source, Object o) {
        System.out.printf("%-10s[TID: %3s : %-30s] %s%n",
                source,
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                o instanceof Page ? ((Page) o).movie.id + " ~> " + ((Page) o).movie.title : o);
    }


    public static Scheduler singleThreaded() {
        return SINGLE_THREADED_SCHEDULER;
    }

    public static Scheduler finishScheduler() {
        return FINISH_SCHEDULER;
    }

    public static void printFile(Path location) {
        try {
            threadInfo("print", location);
            System.out.printf("%nContent of '%s'%n", location);
            System.out.println("===============================================");
            Files.readAllLines(location, Charset.defaultCharset())
                    .forEach(System.out::println);
            System.out.println("===============================================");
        } catch (IOException e) {
            System.err.printf("Can't read file '%s'%n", location);
            e.printStackTrace(System.err);
        }
    }
}
