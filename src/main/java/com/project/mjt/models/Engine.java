package com.project.mjt.models;

import com.project.mjt.services.EuroLevelService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Engine extends BaseEntity{
    private String name;
    private EngineType type;
    private EuroLevelService.EuroLevel euroLevel;

    public Engine(String name) {
        this.name = name;
    }

}
