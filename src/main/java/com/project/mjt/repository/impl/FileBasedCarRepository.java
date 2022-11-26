/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;

@Repository("test")
public class FileBasedCarRepository implements CarRepository {

    private int serialNumber = 1000000;

    List<Car> allCars = new ArrayList<>();

    ObjectMapper mapper = new ObjectMapper();

    private static final String DATA_FILE_NAME = "src/main/resources/json/cars.json";

    private static final String EXPORTED_DATA_FILE_NAME = "src/main/resources/json/cars_exported.json";

    public FileBasedCarRepository() throws IOException {

        loadData();
    }

    @Override
    public List<Car> getCars() {
        return allCars;
    }

    @Override
    public Optional<Car> getCarBySerial(int serialNumber) {
        return allCars.stream().filter(car -> car.getSerialNumber().equals(serialNumber)).findFirst();
    }

    @Override
    public void createCar(Car car) {
        allCars.add(car);
    }

    @Override
    public void updateCar(int serial, Car carUpdate) throws CarNotFoundException {

        Optional<Car> carToUpdate = getCarBySerial(serial);
        if (carToUpdate.isEmpty())
            throw new CarNotFoundException(String.format("Car with serial number %d could not be found.", serialNumber));

        int indexOfCarToUpdate = allCars.indexOf(carToUpdate.get());
        allCars.set(indexOfCarToUpdate, carUpdate);
    }

    @Override
    public void deleteCar(Car car) {
        allCars.remove(car);
    }

    /**
     * Generate a serial number for a new car.
     */
    @Override
    public int generateSerialNumber() {
        return ++serialNumber;
    }


    /**
     * Saves the data in a JSON structured data file.
     */
    @Override
    public void saveData() throws IOException {
        File dataFile = new File(EXPORTED_DATA_FILE_NAME);
        mapper.writeValue(dataFile, allCars);
    }

    /**
     * Loads the data from a JSON file and maps to a list of the entity object 'Car'
     */
    private void loadData() throws IOException {

        File dataFile = new File(DATA_FILE_NAME);

        List<Car> loadedCars = mapper.readValue(dataFile, new TypeReference<List<Car>>() {});

        setStartingSerialNumber(loadedCars);

        allCars.addAll(loadedCars);
    }

    /**
     * Sets the starting serial number depending on the highest serial number stored in the data file.
     */
    private void setStartingSerialNumber(List<Car> cars) {
        Optional<Car> maxSerialNumberCar = cars.stream().max(Comparator.comparing(Car::getSerialNumber));
        serialNumber = maxSerialNumberCar.get().getSerialNumber();
    }
}
