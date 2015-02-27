package com.github.devoxx.server.rest;

/**
 * Created by david.wursteisen on 27/02/2015.
 */
class TranslationNotFound extends RuntimeException {
    public TranslationNotFound(String id, String lang) {
        super(String.format("Could not find translation [%s] for movie %s", id, lang));
    }
}
