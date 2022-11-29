/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services;

import java.util.List;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.exception.CarDeletionException;
import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.exception.CarStoringException;
import com.project.mjt.exception.CarUpdatingException;
import org.springframework.data.domain.Page;

public interface CarService {

    List<CarDTO> getCars(String serialNumber,
                         String model,
                         String brand,
                         String afterYear,
                         String beforeYear) throws CarNotFoundException;

    List<CarDTO>  getCarsPagination(Integer page, Integer elementsInPage);

    CarDTO getCarBySerialNumber(int serialNumber) throws CarNotFoundException;

    CarDTO addNewCar(CarDTO car) throws CarStoringException;

    CarDTO updateCar(String serialNumber, CarDTO carUpdateDTO) throws CarNotFoundException, CarUpdatingException;

    CarDTO deleteCarById(String serialNumber) throws CarNotFoundException, CarDeletionException;

}
