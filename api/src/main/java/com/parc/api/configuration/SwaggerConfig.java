package com.parc.api.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**") // Indique quels chemins inclure dans la documentation
                .build();
    }
}

