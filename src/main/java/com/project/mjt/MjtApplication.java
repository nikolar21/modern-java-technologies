package com.project.mjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication()
public class MjtApplication {

    public static void main(String[] args) {
        SpringApplication.run(MjtApplication.class, args);
    }

}
