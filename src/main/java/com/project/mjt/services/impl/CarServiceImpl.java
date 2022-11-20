package com.project.mjt.services.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.exception.CarDeletionException;
import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.exception.CarStoringException;
import com.project.mjt.exception.CarUpdatingException;
import com.project.mjt.exception.DataSaveException;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;
import com.project.mjt.services.CarService;
import com.project.mjt.services.EuroLevelService;
import com.project.mjt.services.TaxCalculatorService;
import com.project.mjt.services.utils.CarMapper;

@Service
public class CarServiceImpl implements CarService {

    CarRepository carRepository;

    CarMapper carMapper;

    EuroLevelService euroLevelService;

    TaxCalculatorService taxCalculatorService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper, EuroLevelService euroLevelService,
                          TaxCalculatorService taxCalculatorService) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.euroLevelService = euroLevelService;
        this.taxCalculatorService = taxCalculatorService;
    }

    @Override
    public List<CarDTO> getCars(String serialNumber, String model, String brand, String afterYear, String beforeYear,
                                String color) throws CarNotFoundException {

        List<Car> cars = carRepository.getCars();

        if (cars.isEmpty())
            return Collections.emptyList();

        // TODO: Move filtering to the repository layer when a DB is implemented, to avoid unnecessary load on the
        //       repository layer (and the database)
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

        Optional<Car> foundCar = carRepository.getCarBySerial(serialNumber);

        if (foundCar.isEmpty())
            throw new CarNotFoundException(serialNumber);

        return carMapper.toDTO(foundCar.get());
    }

    @Override
    public CarDTO addNewCar(CarDTO car) throws CarStoringException {

        // Retrieve serial number from the repository, which is responsible for keeping track of the serial number
        car.setSerialNumber(carRepository.getSerialNumber());

        // Map DTO object from the controller to an entity object
        Car newCar = carMapper.toEntity(car);

        taxCalculatorService.setTaxBracket(newCar);

        euroLevelService.setEngineStandards(newCar);

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

        // Validate that car is available
        Optional<Car> carToBeUpdated = carRepository.getCarBySerial(serialNumberInt);
        if (carToBeUpdated.isEmpty())
            throw new CarNotFoundException(serialNumberInt);

        // Map front-end DTO object to entity
        Car carUpdate = carMapper.toEntity(carUpdateDTO);

        // Validate that the client did not change the serial number
        carUpdate.setSerialNumber(carToBeUpdated.get().getSerialNumber());

        euroLevelService.setEngineStandards(carUpdate);

        carRepository.updateCar(serialNumberInt, carUpdate);

        // Validate that an update was performed correctly
        Optional<Car> updatedCar = carRepository.getCarBySerial(serialNumberInt);
        if (updatedCar.isEmpty() || !updatedCar.get().equals(carUpdate)) {
            throw new CarUpdatingException(
                String.format("Error occurred while trying to update car with serial number %d.", serialNumberInt));
        }

        return carMapper.toDTO(updatedCar.get());
    }

    @Override
    public CarDTO deleteCarById(String serialNumber) throws CarNotFoundException, CarDeletionException {

        int serialNumberInt = Integer.parseInt(serialNumber);

        Optional<Car> carToBeDeleted = carRepository.getCarBySerial(serialNumberInt);

        if (carToBeDeleted.isEmpty())
            throw new CarNotFoundException(
                    String.format("Car with serial number %d could not be found.", serialNumberInt));

        carRepository.deleteCar(carToBeDeleted.get());

        return carMapper.toDTO(carToBeDeleted.get());
    }

    @Override
    public void saveCarData() throws DataSaveException {
        try {
            carRepository.saveData();
        } catch (IOException e) {
            throw new DataSaveException("Could not save data to JSON file.");
        }
    }
}
