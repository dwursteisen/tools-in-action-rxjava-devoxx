package com.github.devoxx.sandbox.model;

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
