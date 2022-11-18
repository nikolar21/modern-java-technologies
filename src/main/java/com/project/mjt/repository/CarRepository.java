/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.repository;

import java.util.List;

import com.project.mjt.models.Car;

public interface CarRepository {

    List<Car> getCars();

    void createCar(Car car);

    void updateCar(Car carToUpdate);

    void deleteCar(Car car);

    int getSerialNumber();
}
