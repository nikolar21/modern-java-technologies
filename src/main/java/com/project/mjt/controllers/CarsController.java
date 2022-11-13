package com.project.mjt.controllers;

import com.project.mjt.models.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarsController {

    int serialNumber = 101010;
    List<Car> cars = new ArrayList<>();

    @GetMapping("/")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(this.cars);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<List<Car>> getCarById(@PathVariable("serialNumber") int number) {
        List<Car> response = this.cars.stream()
                .filter(result -> result.serialNumber == number)
                .collect(Collectors.toList());

        if (response.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", number));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<Car> addCar(@RequestBody() Car car) {
        car.setSerialNumber(++serialNumber);
        this.cars.add(car);

        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Void> deleteCarById(@PathVariable("serialNumber") int number) {
        List<Car> response = this.cars.stream()
                .filter(result -> result.serialNumber == number)
                .collect(Collectors.toList());

        if (response.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", number));
        }

        Car car = response.get(0);

        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with a serial number: %d is not found", number));
        }

        this.cars.remove(car);

        return null;
    }
}
