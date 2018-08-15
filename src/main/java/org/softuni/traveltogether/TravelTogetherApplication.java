package org.softuni.traveltogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TravelTogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelTogetherApplication.class, args);
    }
}
