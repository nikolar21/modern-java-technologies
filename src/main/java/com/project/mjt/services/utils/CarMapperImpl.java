/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.utils;

import com.project.mjt.models.Engine;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.models.Car;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDTO toDTO(Car car) {
        return new CarDTO(
                car.getSerialNumber(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getEngine().getName());
    }

    @Override
    public Car toEntity(CarDTO car) {
        return Car.builder()
                .engine(
                        new Engine(car.getEngine())
                )
                .serialNumber(car.getSerialNumber())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .build();
    }

    @Override
    public List<CarDTO> toDTO(List<Car> car) {
        return car.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Car> toEntity(List<CarDTO> car) {
        return car.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
