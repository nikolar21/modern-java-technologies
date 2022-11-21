package com.project.mjt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CarDTO {

    private Integer serialNumber;
    private String brand;
    private String model;
    private Integer year;
    private String engine;
}
