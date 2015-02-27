package com.github.devoxx.server.rest;

class TranslationNotFound extends RuntimeException {
    public TranslationNotFound(String id, String lang) {
        super(String.format("Could not find translation [%s] for movie %s", id, lang));
    }
}
