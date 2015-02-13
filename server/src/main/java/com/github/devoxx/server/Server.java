package com.github.devoxx.server;

import com.github.devoxx.server.repository.MovieRepository;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by david.wursteisen on 12/02/2015.
 */
@EnableSwagger
@ComponentScan
@EnableAutoConfiguration
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }


    @Bean
    public MovieRepository movieRepository() {
        return new MovieRepository();
    }

    /**
     * ---- Swagger ---- *
     */

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    // Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .includePatterns("/movies.*")
                .apiInfo(new ApiInfo("Movies Catalogue", "Catalogue de film", "", "", "", ""));
    }

}
