package com.github.devoxx.sandbox.retrofit;

import java.util.concurrent.Executors;

import retrofit.RestAdapter;

/**
 * Created by david.wursteisen on 27/02/2015.
 */
public class ApiFactory {

    private final RestAdapter adapter;

    public ApiFactory() {
        adapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .setExecutors(Executors.newFixedThreadPool(4), Executors.newFixedThreadPool(4))
                .build();

    }

    public ObservableServerApi observable() {
        return adapter.create(ObservableServerApi.class);
    }

    public ServerApi synchrone() {
        return adapter.create(ServerApi.class);
    }

    public CallbackServerApi callback() {
        return adapter.create(CallbackServerApi.class);
    }
}
