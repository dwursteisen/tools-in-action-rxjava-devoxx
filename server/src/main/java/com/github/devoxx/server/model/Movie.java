package com.github.devoxx.server.model;

import java.util.UUID;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
public class Movie {

    public Movie(String title) {
        this.title = title;
    }

    public Movie() {
    }

    public final String id = UUID.randomUUID().toString();

    public String title;

}
