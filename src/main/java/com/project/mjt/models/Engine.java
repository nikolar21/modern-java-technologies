package com.project.mjt.models;

import com.project.mjt.services.EuroLevelService;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Engine extends BasicEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private EngineType type;

    private EuroLevelService.EuroLevel euroLevel;

    @OneToMany(targetEntity =Car.class,mappedBy = "engine")
    private List<Car> carList;

    public Engine(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", euroLevel=" + euroLevel +
                '}';
    }
}
