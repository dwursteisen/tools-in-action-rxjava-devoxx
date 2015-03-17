package com.github.devoxx.server.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ApiConfig.class)
public class InPoneyLand {

    @Bean
    public int port() {
        return 8080;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new ContainerCustomizer();
    }

    public class ContainerCustomizer implements EmbeddedServletContainerCustomizer {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.setPort(port());
        }
    }
}
