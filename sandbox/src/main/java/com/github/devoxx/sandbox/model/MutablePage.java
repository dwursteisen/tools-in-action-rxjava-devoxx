package com.github.devoxx.sandbox.model;

import java.util.List;


public class MutablePage {

    Movie movie;
    Synopsis synopsis;
    List<Actor> actors;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Synopsis getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(Synopsis synopsis) {
        this.synopsis = synopsis;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "MutablePage{" +
                "movie=" + movie +
                ", synopsis=" + synopsis +
                ", actors=" + actors +
                '}';
    }
}
