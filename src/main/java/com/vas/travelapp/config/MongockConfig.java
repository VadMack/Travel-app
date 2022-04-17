package com.vas.travelapp.config;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("production")
@EnableMongock
@Configuration
public class MongockConfig {
}
