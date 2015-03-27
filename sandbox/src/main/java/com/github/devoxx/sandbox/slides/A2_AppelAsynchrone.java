package com.github.devoxx.sandbox.slides;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ServerApi;

// D
public class A2_AppelAsynchrone {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ServerApi api = new ApiFactory().synchrone();

    }
}
