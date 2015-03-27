package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ServerApi;

// D
public class A1_AppelSynchrone {

    public static void main(String[] args) {
        ServerApi api = new ApiFactory().synchrone();

    }
}
