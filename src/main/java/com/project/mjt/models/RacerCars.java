package com.project.mjt.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "racer_cars")
public class RacerCars {

    @EmbeddedId
    private RacerCarsFK racerCarsFK;
}