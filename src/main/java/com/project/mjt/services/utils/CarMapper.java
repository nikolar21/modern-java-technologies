/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.utils;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.models.Car;

import java.util.List;

public interface CarMapper {

    /**
     * Map the data received from the client (for example the front-end) to the data that needs to be stored in the
     * repository (the database in the future).
     */
    CarDTO toDTO(Car car);

    /**
     * Map the data recieved from the repository (from the
     */
    Car toEntity(CarDTO car);

    List<CarDTO> toDTO(List<Car> car);

    List<Car> toEntity(List<CarDTO> car);
}
