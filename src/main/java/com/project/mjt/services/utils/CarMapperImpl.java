/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.utils;

import com.project.mjt.models.Engine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.models.Car;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<CarDTO> toListDTO(List<Car> car) {
        return convertToList(car.stream());
    }

    public List<CarDTO> toListDTO(Page<Car> car) {
        return convertToList(car.stream());
    }

    private List<CarDTO> convertToList (Stream<Car> stream){
        return stream.map(this::toDTO).collect(Collectors.toList());
    }

}
