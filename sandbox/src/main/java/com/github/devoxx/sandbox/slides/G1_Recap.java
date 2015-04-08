package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;

import java.util.concurrent.CountDownLatch;

// B, D code si nécessaire
public class G1_Recap {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ObservableServerApi api = new ApiFactory().observable();

        // recap

        latch.await();

    }

}
