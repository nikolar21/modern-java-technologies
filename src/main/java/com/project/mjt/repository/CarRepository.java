/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.models.Car;

public interface CarRepository {

    List<Car> getCars();

    Optional<Car> getCarBySerial(int serialNumber);

    void createCar(Car car);

    void updateCar(int serial, Car carUpdate) throws CarNotFoundException;

    void deleteCar(Car car);

    int getSerialNumber();

    void saveData() throws IOException;
}
