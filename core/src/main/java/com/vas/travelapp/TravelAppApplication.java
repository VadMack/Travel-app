package com.vas.travelapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableScheduling
@EnableAutoConfiguration
@SpringBootApplication
@EnableEurekaClient
public class TravelAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelAppApplication.class, args);
    }
}
