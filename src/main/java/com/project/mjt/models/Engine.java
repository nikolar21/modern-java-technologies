package com.project.mjt.models;

import com.project.mjt.services.EuroLevelService;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Engine extends BaseEntity{

    private String name;

    @Enumerated(EnumType.STRING)
    private EngineType type;

    private EuroLevelService.EuroLevel euroLevel;

    public Engine(String name) {
        this.name = name;
    }

}
