/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.utils;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.models.Car;

public interface CarMapper {

    CarDTO toDTO(Car car);

    Car toEntity(CarDTO car);
}
