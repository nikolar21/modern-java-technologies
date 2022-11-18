/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.utils;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.models.Car;

@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDTO toDTO(Car car) {
        return new CarDTO(
                car.getSerialNumber(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getEngine());
    }

    @Override
    public Car toEntity(CarDTO car) {
        return Car.builder()
                .engine(car.getEngine())
                .serialNumber(car.getSerialNumber())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .build();
    }
}