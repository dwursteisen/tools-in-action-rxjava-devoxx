package com.github.devoxx.sandbox.model;

import java.util.Arrays;
import java.util.List;

public class Page {
    public Movie movie;
    public Synopsis synopsis;
    public List<Actor> actors;

    public Page() {
    }

    public Page(Movie movie, Synopsis synopsis, List<Actor> actors) {
        this.movie = movie;
        this.synopsis = synopsis;
        this.actors = actors;
    }

    public Page(Movie movie, List<Actor> actors, Synopsis synopsis) {
        this.movie = movie;
        this.synopsis = synopsis;
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Page{" +
                "movie=" + movie +
                ",\n\t synopsis=" + synopsis +
                ",\n\t actors=" + Arrays.toString(actors.toArray()) +
                "}\n\n";
    }
}
