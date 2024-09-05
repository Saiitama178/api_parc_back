package com.parc.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parc Advisor API")
                        .version("1.0")
                        .description("Documentation de l'API pour Parc Advisor.")
                        .contact(new Contact()
                                .name("Support")
                                .url("http://www.example.com/support")
                                .email("support@example.com")));
    }
}

