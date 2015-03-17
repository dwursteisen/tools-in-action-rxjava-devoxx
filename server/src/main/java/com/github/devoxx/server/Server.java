package com.github.devoxx.server;

import org.springframework.boot.builder.SpringApplicationBuilder;
import com.github.devoxx.server.config.InPoneyLand;
import com.github.devoxx.server.config.InProduction;

public class Server {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(InPoneyLand.class).run(args);
        new SpringApplicationBuilder().sources(InProduction.class).run(args);
    }

}
