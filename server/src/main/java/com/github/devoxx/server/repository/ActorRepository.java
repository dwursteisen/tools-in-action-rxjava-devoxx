package com.github.devoxx.server.repository;

import java.util.List;

import com.github.devoxx.server.model.Actor;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by david.wursteisen on 17/02/2015.
 */
public class ActorRepository {

    private final MovieRepository movieRepository;

    @Autowired
    public ActorRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Actor> actors() {
        return movieRepository.actors();
    }

    public String bio(String id) {
        return new Faker().lorem().paragraph();
    }
}
