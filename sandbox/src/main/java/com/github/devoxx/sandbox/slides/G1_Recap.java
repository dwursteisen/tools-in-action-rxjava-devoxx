package com.github.devoxx.sandbox.slides;

import java.util.concurrent.CountDownLatch;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.CSV;

// B, D code si nécessaire
public class G1_Recap {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        // recap

        latch.await();

    }

}
