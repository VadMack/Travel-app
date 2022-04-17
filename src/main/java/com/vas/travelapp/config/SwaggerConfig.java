package com.vas.travelapp.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${app-version}") String version) {
        return new OpenAPI()
                .info(new Info().title("Travel-app")
                        .version(version)
                        .description("Course work №3")
                );
    }
}
