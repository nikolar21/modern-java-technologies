package com.project.mjt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CarDTO {
    private int serialNumber;
    private String brand;
    private String model;
    private int year;
    private String engine;
    private float price;
}
