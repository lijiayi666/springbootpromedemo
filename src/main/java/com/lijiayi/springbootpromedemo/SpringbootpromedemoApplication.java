package com.lijiayi.springbootpromedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootpromedemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootpromedemoApplication.class, args);
    }

}
