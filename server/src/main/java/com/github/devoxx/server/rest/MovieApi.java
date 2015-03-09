package com.github.devoxx.server.rest;

import java.util.List;
import java.util.Random;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;
import com.github.devoxx.server.repository.MovieRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Movies")
@RestController("Movie Controller")
public class MovieApi {

    private final MovieRepository repository;
    private final Random random = new Random();

    @Value(value = "${rest.wait.time}")
    private int waitTime = 0;

    @Autowired
    public MovieApi(MovieRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> movies() {
        sleep();
        return repository.movies();
    }

    @RequestMapping(value = "/movies/cast/{id}", method = RequestMethod.GET)
    public List<Actor> cast(@PathVariable("id") String id) {
        if (!id.equals("pulpfiction")) {
            sleep(); // no sleep for pulp fiction
        }
        return repository.cast(id);
    }

    @RequestMapping(value = "/movies/synopsis/{id}", method = RequestMethod.GET)
    public Synopsis synopsis(@PathVariable("id") String id) {
        sleep();
        if (id.equals("thedarkknight")) {
            sleep(); // visious sleep
        }
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

    private void sleep() {
        try {
            Thread.sleep(waitTime + random.nextInt(1000));
        } catch (InterruptedException e) {
            System.err.println("oups : Interrupted !");
        }
    }
}
