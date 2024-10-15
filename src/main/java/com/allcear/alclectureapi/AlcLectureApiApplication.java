package com.allcear.alclectureapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AlcLectureApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlcLectureApiApplication.class, args);
    }

}
