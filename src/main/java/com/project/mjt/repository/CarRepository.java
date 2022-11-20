/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.project.mjt.models.Car;

public interface CarRepository {

    List<Car> getCars();

    Optional<Car> getCarBySerial(int serialNumber);

    void createCar(Car car);

    void updateCar(Car carToUpdate);

    void deleteCar(Car car);

    int getSerialNumber();

    void saveJSON() throws IOException;
}
