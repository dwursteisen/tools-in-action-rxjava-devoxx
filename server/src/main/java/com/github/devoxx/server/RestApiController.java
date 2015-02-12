package com.github.devoxx.server;

import java.util.List;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
@RestController("Movie Controller")
public class RestApiController {

    private final MovieRepository repository;

    @Autowired
    public RestApiController(MovieRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> movies() {
        return repository.movies();
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    public List<Actor> movieDetail(@PathVariable("id") String id) {
        return repository.actors(id);
    }
}
