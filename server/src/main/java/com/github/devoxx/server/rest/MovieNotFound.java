package com.github.devoxx.server.rest;

import static java.lang.String.format;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotFound extends RuntimeException {

    public MovieNotFound(String id) {
        super(format("Movie with '%s' not found", id));
    }
}
