package com.github.devoxx.sandbox.slides;

import java.util.concurrent.CountDownLatch;

import com.github.devoxx.sandbox.retrofit.ApiFactory;
import com.github.devoxx.sandbox.retrofit.ObservableServerApi;
import com.github.devoxx.sandbox.tooling.CSV;

// B, D code si nécessaire
public class G1_Schedulers {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ObservableServerApi api = new ApiFactory().reliablePartner().observable();

        // on each print thread info
        // open CSV -> subscribe and append, print err, close csv
        CSV csv = new CSV().open();

        // 1) api.movies() -> build page
        // 2) display thread using :
        // .doOnEach(Tools::threadInfo)
        // 3) onCompleted : close and  Tools.printFile(csv.location)

        latch.await();

    }

}
