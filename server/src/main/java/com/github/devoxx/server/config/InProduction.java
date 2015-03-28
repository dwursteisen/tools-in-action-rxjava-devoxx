package com.github.devoxx.server.config;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;
import com.github.devoxx.server.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Configuration
@Import(ApiConfig.class)
public class InProduction {

    @Bean
    public int port() {
        return 8666;
    }

    @Value(value = "${rest.wait.time}")
    private int waitTime = 0;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port());
        factory.setSessionTimeout(10, TimeUnit.SECONDS);
        // maybe more config
        return factory;
    }

    @Bean
    public MovieRepository movieRepository() {
        return new MovieRepository(port()) {
            private final Random random = new Random();

            @Override
            public List<Movie> movies() {
                sleep();
                return super.movies();
            }

            @Override
            public List<Actor> cast(String id) {
                if (!id.equals("pulpfiction")) {
                    sleep(); // no sleep for pulp fiction
                }
                return super.cast(id);
            }

            @Override
            public Synopsis synopsis(String id) {
                sleep();
                if (id.equals("thedarkknight")) {
                    sleep(); // vicious sleep
                }
                return super.synopsis(id);
            }

            private void sleep() {
                try {
                    TimeUnit.MILLISECONDS.sleep(waitTime);
                } catch (InterruptedException e) {
                    System.err.println("oups : Interrupted !");
                }
            }
        };
    }
}
