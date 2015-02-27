package com.github.devoxx.sandbox.model;

public class Synopsis {

    public String synopsis;
    public String posterUrl;

    public Synopsis(String synopsis, String posterUrl) {
        this.synopsis = synopsis;
        this.posterUrl = posterUrl;
    }

    public Synopsis() {
    }

    @Override
    public String toString() {
        return "Synopsis{" +
                "synopsis='" + synopsis + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
