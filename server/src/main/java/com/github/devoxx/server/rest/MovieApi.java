package com.github.devoxx.server.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;
import com.github.devoxx.server.repository.MovieRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api("Movies")
@RestController("Movie Controller")
public class MovieApi {

    private final MovieRepository repository;

    @Autowired
    public MovieApi(MovieRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    public Movie movie(@PathVariable("id") String id) {
        return repository.movie(id).orElseThrow(() -> new MovieNotFound(id));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> movies() {
        return repository.movies();
    }

    @RequestMapping(value = "/movies/cast/{id}", method = RequestMethod.GET)
    public List<Actor> cast(@PathVariable("id") String id) {
        return repository.cast(id);
    }

    @RequestMapping(value = "/movies/synopsis/{id}", method = RequestMethod.GET)
    public Synopsis synopsis(@PathVariable("id") String id) {
        return repository.synopsis(id);
    }

    @RequestMapping(value = "/movies/translation/{id}/{lang}", method = RequestMethod.GET)
    public Movie translation(@PathVariable("id") String id, @PathVariable("lang") String lang) {
        return repository.translation(id, lang).orElseThrow(() -> new TranslationNotFound(id, lang));
    }

    @ApiOperation(value = "Ancien service de traduction. Peut planter de temps en temps...")
    @RequestMapping(value = "/movies/traduction/{id}/{lang}", method = RequestMethod.GET)
    public Movie traduction(@PathVariable("id") String id, @PathVariable("lang") String lang) {
        if (id.length() % 2 == 0) {
            throw new RuntimeException("** BOOM **");
        }
        return translation(id, lang);
    }
}
