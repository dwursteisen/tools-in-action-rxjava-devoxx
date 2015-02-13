package com.github.devoxx.sandbox.model;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
public class Actor {

    public String firstName;
    public String lastName;

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor() {
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
