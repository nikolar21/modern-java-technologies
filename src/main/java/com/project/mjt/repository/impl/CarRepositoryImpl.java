/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.repository.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private int serialNumber = 1000000;

    List<Car> allCars = new ArrayList<>();

    public CarRepositoryImpl() throws IOException {

        String DATA_FILE_NAME = "src/main/resources/json/cars.json";
        File dataFile = new File(DATA_FILE_NAME);

        // TODO: Refactor
        ObjectMapper mapper = new ObjectMapper();
        List<Car> loadedCars = mapper.readValue(dataFile, new TypeReference<List<Car>>() {});
        Optional<Car> maxSerialNumberCar = loadedCars.stream().max(Comparator.comparing(Car::getSerialNumber));
        serialNumber = maxSerialNumberCar.get().getSerialNumber();

        allCars.addAll(loadedCars);
    }

    @Override
    public List<Car> getCars() {
        return allCars;
    }

    @Override
    public void createCar(Car car) {
        allCars.add(car);
    }

    @Override
    public void updateCar(Car carToUpdate) {
        int index;
        // TODO: Refactor this
        for(index = 0; index < allCars.size(); index++) {
            if (allCars.get(index).getSerialNumber().equals(carToUpdate.getSerialNumber()))
                allCars.set(index, carToUpdate);
        }
    }

    @Override
    public void deleteCar(Car car) {
        allCars.remove(car);
    }

    @Override
    public int getSerialNumber() {
        return ++serialNumber;
    }
}
