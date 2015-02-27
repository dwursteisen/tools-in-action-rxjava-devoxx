package com.github.devoxx.server.model;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
public class Movie {

    public Movie(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Movie(String title) {
        this.title = title;
        this.id = titleToId(title);
    }

    public static String titleToId(String title) {
        return title.toLowerCase().trim().replaceAll("[\\W]", "");
    }

    public Movie() {
        id = "";
    }

    public final String id;

    public String title;

}
