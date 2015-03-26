package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.client.Clients;
import com.github.devoxx.sandbox.operators.Throttler;

import java.util.concurrent.TimeUnit;

/**
 * Created by david.wursteisen on 11/03/2015.
 */
public class H2_BackPressure {

    public static void main(String[] args) throws InterruptedException {
        Clients.sending_lot_of_refresh_queries()
                .onBackpressureDrop()
                .lift(new Throttler<>(1, TimeUnit.SECONDS))
                .toBlocking()
                .forEach(System.out::println);

    }

}
