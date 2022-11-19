package com.project.mjt.services.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.exception.CarDeletionException;
import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.exception.CarStoringException;
import com.project.mjt.exception.CarUpdatingException;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;
import com.project.mjt.services.CarService;
import com.project.mjt.services.utils.CarMapper;

@Service
public class CarServiceImpl implements CarService {

    CarRepository carRepository;

    CarMapper carMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public List<CarDTO> getAllCars() {

        List<Car> cars = this.carRepository.getCars();

        if (cars.size() == 0) {
            return Collections.emptyList();
        }

        List<CarDTO> carsDTO = cars.stream().map(car -> carMapper.toDTO(car)).collect(Collectors.toList());
        return carsDTO;
    }

    @Override
    public List<CarDTO> getCars(String serialNumber, String model, String brand, String afterYear, String beforeYear,
                                String color) throws CarNotFoundException {

        List<Car> cars = carRepository.getCars();

        if (serialNumber != null) {
            int serialNumberInt = Integer.parseInt(serialNumber);

            return cars
                    .stream()
                    .filter(car -> car.getSerialNumber().equals(serialNumberInt))
                    .map(carMapper::toDTO)
                    .collect(Collectors.toList());
        }
        if (model != null && !model.isBlank()) {
            cars = cars.stream().filter(car -> car.getModel().equalsIgnoreCase(model)).collect(Collectors.toList());
        }
        if (brand != null && !brand.isBlank()) {
            cars = cars.stream().filter(car -> car.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());
        }
        if (afterYear != null && !afterYear.isBlank()) {
            int afterYearInt = Integer.parseInt(afterYear);
            cars = cars.stream().filter(car -> car.getYear() >= afterYearInt).collect(Collectors.toList());
        }
        if (beforeYear != null && !beforeYear.isBlank()) {
            int beforeYearInt = Integer.parseInt(beforeYear);
            cars = cars.stream().filter(car -> car.getYear() <= beforeYearInt).collect(Collectors.toList());
        }
        if (color != null && !color.isBlank()) {
            cars = cars.stream().filter(car -> car.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
        }

        if (cars.isEmpty())
            throw new CarNotFoundException("No cars were found with the given parameters.");

        return cars.stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO getCarBySerialNumber(int serialNumber) throws CarNotFoundException {

        List<Car> cars = carRepository.getCars();
        Optional<Car> foundCar = cars.stream().filter(car -> car.getSerialNumber().equals(serialNumber)).findFirst();

        if (foundCar.isEmpty())
            throw new CarNotFoundException(String.format("CarDTO with a serial number: %d is not found", serialNumber));

        return carMapper.toDTO(foundCar.get());
    }

    @Override
    public CarDTO addNewCar(CarDTO car) throws CarStoringException {

        car.setSerialNumber(carRepository.getSerialNumber());
        Car newCar = carMapper.toEntity(car);

        carRepository.createCar(newCar);

        try {
            return getCarBySerialNumber(newCar.getSerialNumber());
        } catch (CarNotFoundException e) {
            throw new CarStoringException(
                String.format("Error occurred while trying to store car with serial number %d.", car.getSerialNumber()));
        }
    }

    @Override
    public CarDTO updateCar(String serialNumber, CarDTO carUpdateDTO) throws CarNotFoundException, CarUpdatingException {

        // TODO: Validate if number is in integer range
        int serialNumberInt = Integer.parseInt(serialNumber);

        getCarBySerialNumber(serialNumberInt);

        Car carUpdate = carMapper.toEntity(carUpdateDTO);

        carRepository.updateCar(carUpdate);

        CarDTO updatedCar = getCarBySerialNumber(serialNumberInt);

        if (!updatedCar.equals(carUpdateDTO)) {
            throw new CarUpdatingException(
                String.format("Error occurred while trying to update car with serial number %d.", serialNumberInt));
        }

        return updatedCar;
    }

    @Override
    public CarDTO deleteCarById(String serialNumber) throws CarNotFoundException, CarDeletionException {

        int serialNumberInt = Integer.parseInt(serialNumber);

        CarDTO carToBeDeleted = getCarBySerialNumber(serialNumberInt);

        carRepository.deleteCar(carMapper.toEntity(carToBeDeleted));

        try {
            getCarBySerialNumber(serialNumberInt);
        } catch (CarNotFoundException e) {
            throw new CarDeletionException(
                    String.format("Error occurred while trying to delete car with serial number %d.", serialNumberInt));
        }

        return carToBeDeleted;
    }
}
