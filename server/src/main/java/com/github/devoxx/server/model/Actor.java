package com.github.devoxx.server.model;

import java.util.UUID;

public class Actor {

    public final String id = UUID.randomUUID().toString();

    public String firstName;
    public String lastName;

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor() {
    }

    public boolean match(String criteria) {
        return firstName.toLowerCase().startsWith(criteria.toLowerCase()) || lastName.toLowerCase().startsWith(criteria.toLowerCase());
    }
}
