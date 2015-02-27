package com.github.devoxx.sandbox.model;

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
