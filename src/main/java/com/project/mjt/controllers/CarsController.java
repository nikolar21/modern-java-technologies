package com.project.mjt.controllers;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.exception.CarDeletionException;
import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.exception.CarStoringException;
import com.project.mjt.exception.CarUpdatingException;
import com.project.mjt.exception.DataSaveException;
import com.project.mjt.services.impl.CarServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.SneakyThrows;

@RestController
@RequestMapping("/cars")
@CrossOrigin("http://localhost:4200/")
public class CarsController {

    private final CarServiceImpl carService;

    @Autowired
    public CarsController(CarServiceImpl carService) {
        this.carService = carService;
    }

    // ---------------
    // CRUD OPERATIONS
    // ---------------

//    @GetMapping()
//    public ResponseEntity<List<CarDTO>> getAllCars() {
//        return ResponseEntity.ok(carService.getAllCars());
//    }

    @SneakyThrows
    @GetMapping("/{serialNumber}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("serialNumber") int number) {
        return ResponseEntity.ok(carService.getCarBySerialNumber(number));
    }

    @SneakyThrows
    @GetMapping()
    public ResponseEntity<List<CarDTO>> getCars(
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String afterYear,
            @RequestParam(required = false) String beforeYear,
            @RequestParam(required = false) String color
    ) {
        return ResponseEntity.ok(carService.getCars(
                serialNumber,
                model,
                brand,
                afterYear,
                beforeYear,
                color
        ));
    }

    @SneakyThrows
    @PostMapping()
    public ResponseEntity<CarDTO> addCar(@RequestBody() CarDTO car) {
        return new ResponseEntity<>(carService.addNewCar(car), HttpStatus.CREATED);
    }

    @SneakyThrows
    @PutMapping("/{serialNumber}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable("serialNumber") String serialNumber, @RequestBody() CarDTO car) {
        return new ResponseEntity<>(carService.updateCar(serialNumber, car), HttpStatus.CREATED);
    }

    @SneakyThrows
    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<CarDTO> deleteCarById(@PathVariable("serialNumber") String serialNumber) {
        return new ResponseEntity<>(carService.deleteCarById(serialNumber), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/save")
    public ResponseEntity saveData() {
        carService.saveCarData();
        return ResponseEntity.ok("Successfully saved data into JSON.");
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    @ExceptionHandler(value = CarDeletionException.class)
    private ResponseEntity<String> handleCarDeletionException(CarDeletionException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = CarUpdatingException.class)
    private ResponseEntity<String> handleCarUpdatingException(CarUpdatingException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = CarStoringException.class)
    private ResponseEntity<String> handleCarStoringException(CarStoringException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = CarNotFoundException.class)
    private ResponseEntity<String> handleCarNotFoundException(CarNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = DataSaveException.class)
    private ResponseEntity<String> handleDataSaveException(DataSaveException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
