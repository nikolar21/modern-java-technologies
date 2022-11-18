package com.project.mjt.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int serialNumber;
    private String brand;
    private String model;
    private String year;
    private String engine;
}
