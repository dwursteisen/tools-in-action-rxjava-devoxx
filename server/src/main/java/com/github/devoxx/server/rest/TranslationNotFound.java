package com.github.devoxx.server.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TranslationNotFound extends RuntimeException {
    public TranslationNotFound(String id, String lang) {
        super(String.format("Could not find translation [%s] for movie %s", id, lang));
    }
}
