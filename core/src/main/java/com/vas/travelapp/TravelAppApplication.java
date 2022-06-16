package com.vas.travelapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableEurekaClient
public class TravelAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelAppApplication.class, args);
    }
}
