package com.project.mjt.controllers;

import com.project.mjt.models.Car;
import com.project.mjt.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    private CarService carService;

    @GetMapping()
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<Car> getCarById(@PathVariable("serialNumber") int number) {
        return ResponseEntity.ok(carService.getCarBySerialNumber(number));
    }

    @PostMapping()
    public ResponseEntity<Car> addCar(@RequestBody() Car car) {
        return new ResponseEntity<>(carService.addNewCar(car), HttpStatus.CREATED);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<Car> updateCar(@PathVariable("serialNumber") int number, @RequestBody() Car car) {
        return new ResponseEntity<>(carService.updateCar(number, car), HttpStatus.CREATED);
    }

    @DeleteMapping("/{serialNumber}")
    public void deleteCarById(@PathVariable("serialNumber") int number) {
        carService.deleteCarById(number);
    }
}
