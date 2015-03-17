package com.github.devoxx.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.github.devoxx.server.repository.ActorRepository;
import com.github.devoxx.server.repository.MovieRepository;
import com.github.devoxx.server.util.RequestProcessingTimeInterceptor;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
@ComponentScan({
        "com.github.devoxx.server.repository",
        "com.github.devoxx.server.rest",
        "com.github.devoxx.server.util"
})
@EnableAutoConfiguration
public class ApiConfig extends WebMvcConfigurerAdapter {
    @Autowired
    int port;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestProcessingTimeInterceptor());
        super.addInterceptors(registry);
    }

    @Bean
    public MovieRepository movieRepository() {
        return new MovieRepository(port);
    }

    @Bean
    public ActorRepository actorRepository(MovieRepository movieRepository) {
        return new ActorRepository(movieRepository);
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
                .includePatterns("/movies.*", "/actors.*", "/utils.*")
                .apiInfo(new ApiInfo("Movies Catalogue", "Catalogue de film", "", "", "", ""));
    }
}
