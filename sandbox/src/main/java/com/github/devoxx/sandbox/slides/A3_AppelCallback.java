package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.CallbackServerApi;

// D code ; B parle
public class A3_AppelCallback {

    public static void main(String[] args) {
        CallbackServerApi api = new ApiFactory().callback();

    }
}
