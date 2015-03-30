package com.github.devoxx.server.rest;

import static java.lang.String.format;

public class MovieNotFound extends RuntimeException {

    public MovieNotFound(String id) {
        super(format("Movie with '%s' not found", id));
    }
}
