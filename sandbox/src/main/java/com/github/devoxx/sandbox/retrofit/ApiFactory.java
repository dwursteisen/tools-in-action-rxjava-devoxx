package com.github.devoxx.sandbox.retrofit;

import java.util.concurrent.Executors;
import retrofit.RestAdapter;

public class ApiFactory {

    public static final String STANDARD_DEV_LOCALHOST = "http://localhost:8080";
    public static final String UNRELIABLE_LOCALHOST = "http://localhost:8666";

    private final RestAdapter adapter;

    public ApiFactory() {
        this(STANDARD_DEV_LOCALHOST);
    }

    public ApiFactory(String endPoint) {
        adapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
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

    public ApiFactory unreliablePartner() {
        return new ApiFactory(UNRELIABLE_LOCALHOST);
    }
}
