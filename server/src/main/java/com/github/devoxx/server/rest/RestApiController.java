package com.github.devoxx.server.rest;

import java.util.List;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;
import com.github.devoxx.server.repository.MovieRepository;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
@Api("Movies")
@RestController("Movie Controller")
public class RestApiController {

    private final MovieRepository repository;

    @Value(value = "${rest.wait.time}")
    private int waitTime = 0;

    @Autowired
    public RestApiController(MovieRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> movies() {
        sleep();
        return repository.movies();
    }

    @RequestMapping(value = "/movies/cast/{id}", method = RequestMethod.GET)
    public List<Actor> cast(@PathVariable("id") String id) {
        sleep();
        return repository.cast(id);
    }

    @RequestMapping(value = "/movies/synopsis/{id}", method = RequestMethod.GET)
    public Synopsis synopsis(@PathVariable("id") String id) {
        sleep();
        return repository.synopsis(id);
    }

    private void sleep() {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            ; // ignore
        }
    }
}
