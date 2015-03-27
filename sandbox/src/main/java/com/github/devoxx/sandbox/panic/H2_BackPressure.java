package com.github.devoxx.sandbox.panic;

import java.util.concurrent.TimeUnit;

import com.github.devoxx.sandbox.client.Clients;
import com.github.devoxx.sandbox.operators.Throttler;

// B reprend la main pour les obs sans backpressure
public class H2_BackPressure {

    public static void main(String[] args) throws InterruptedException {
        Clients.sending_lot_of_refresh_queries()
                .onBackpressureDrop()
                .lift(new Throttler<>(1, TimeUnit.SECONDS))
                .toBlocking()
                .forEach(System.out::println);

    }

}
