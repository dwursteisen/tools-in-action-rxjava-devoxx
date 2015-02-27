package com.github.devoxx.sandbox.model;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
public class Movie {

    public Movie(String title) {
        this.title = title;
    }

    public Movie() {
    }

    public String id;
    public String title;

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                '}';
    }
}
