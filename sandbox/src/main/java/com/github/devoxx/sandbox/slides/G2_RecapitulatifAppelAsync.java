package com.github.devoxx.sandbox.slides;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;

import java.util.concurrent.TimeUnit;

public class G2_RecapitulatifAppelAsync {

    public static void main(String[] args) throws InterruptedException {
        ObservableServerApi api = new ApiFactory().observable();


        TimeUnit.SECONDS.sleep(5);
    }
}
