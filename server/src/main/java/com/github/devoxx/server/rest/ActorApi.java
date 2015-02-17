package com.github.devoxx.server.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.repository.ActorRepository;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by david.wursteisen on 17/02/2015.
 */
@Api("Actors")
@RestController("Actors")
public class ActorApi {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorApi(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @RequestMapping(value = "/actors/search/{criteria}", method = RequestMethod.GET)
    public List<Actor> search(@PathVariable("criteria") final String criteria) {
        return actors().stream()
                .filter((a) -> a.match(criteria))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/actors/", method = RequestMethod.GET)
    public List<Actor> actors() {
        return actorRepository.actors();
    }

    @RequestMapping(value = "/actors/{id}", method = RequestMethod.GET)
    public String bio(@PathVariable("id") String id) {
        return actorRepository.bio(id);
    }
}
