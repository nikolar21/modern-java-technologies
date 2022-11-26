package com.project.mjt.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class Configuration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
