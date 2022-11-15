package com.project.mjt.services;

import com.project.mjt.models.Car;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    int serialNumber = 101010;
    List<Car> cars = new ArrayList<>();

    public List<Car> getAllCars() {
        return this.cars;
    }

    public Car getCarBySerialNumber(int serialNumber) {
        List<Car> response = this.cars.stream()
                .filter(result -> result.getSerialNumber() == serialNumber)
                .collect(Collectors.toList());

        if (response.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", serialNumber));
        }

        return response.get(0);
    }

    public Car addNewCar(Car car) {
        car.setSerialNumber(++serialNumber);
        this.cars.add(car);

        return car;
    }

    public Car updateCar(int serialNumber, Car car) {
        Car foundCar = this.cars.stream()
                .filter(result -> result.getSerialNumber() == serialNumber)
                .findFirst()
                .orElse(null);

        if (foundCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", serialNumber));
        }

        car.setSerialNumber(foundCar.getSerialNumber());
        this.cars.remove(foundCar);
        this.cars.add(car);

        return car;

    }

    public void deleteCarById(int serialNumber) {
        List<Car> response = this.cars.stream()
                .filter(result -> result.getSerialNumber() == serialNumber)
                .collect(Collectors.toList());

        if (response.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", serialNumber));
        }

        Car car = response.get(0);

        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", serialNumber));
        }

        this.cars.remove(car);

    }
}
