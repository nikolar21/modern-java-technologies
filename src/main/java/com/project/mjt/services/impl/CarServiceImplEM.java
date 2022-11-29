package com.project.mjt.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.mjt.services.SerialNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.project.mjt.dto.CarDTO;
import com.project.mjt.exception.CarDeletionException;
import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.exception.CarStoringException;
import com.project.mjt.exception.CarUpdatingException;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;
import com.project.mjt.services.CarService;
import com.project.mjt.services.EuroLevelService;
import com.project.mjt.services.utils.CarMapper;

@Service("carServiceEM")
public class CarServiceImplEM implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final EuroLevelService euroLevelService;

    private final SerialNumberGenerator serialNumberGenerator;

    @Autowired
    public CarServiceImplEM(CarRepository carRepository, CarMapper carMapper, EuroLevelService euroLevelService, SerialNumberGenerator serialNumberGenerator) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.euroLevelService = euroLevelService;
        this.serialNumberGenerator = serialNumberGenerator;
    }

    @Override
    public List<CarDTO> getCars(String serialNumber, String model, String brand, String afterYear, String beforeYear)
            throws CarNotFoundException {

        if (serialNumber != null) {
            return getCarFilteredBySerialNumber(serialNumber);
        }

        List<Car> cars = carRepository.getCars();

        if (cars.isEmpty())
            return Collections.emptyList();

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

        if (cars.isEmpty())
            throw new CarNotFoundException("No cars were found with the given parameters.");

        return carMapper.toListDTO(cars);
    }

    @Override
    public List<CarDTO> getCarsPagination(Integer page, Integer elementsInPage) {
        return carMapper.toListDTO(carRepository.getCars(page, elementsInPage));
    }

    private List<CarDTO> getCarFilteredBySerialNumber(String serialNumber) {

        int serialNumberInt = Integer.parseInt(serialNumber);
        List<Car> result = new ArrayList<>();

        Optional<Car> carBySerial = carRepository.getCarBySerial(serialNumberInt);
        carBySerial.ifPresent(result::add);

        return carMapper.toListDTO(result);
    }

    @Override
    public CarDTO getCarBySerialNumber(int serialNumber) throws CarNotFoundException {

        Optional<Car> foundCar = carRepository.getCarBySerial(serialNumber);

        if (foundCar.isEmpty())
            throw new CarNotFoundException(String.format("Car with serial number %d could not be found.", serialNumber));

        return carMapper.toDTO(foundCar.get());
    }

    @Override
    public CarDTO addNewCar(CarDTO car) throws CarStoringException {

        // Retrieve serial number from the repository, which is responsible for keeping track of the serial number
        car.setSerialNumber(serialNumberGenerator.generateSerialNumber());

        // Map DTO object from the controller to an entity object
        Car newCar = carMapper.toEntity(car);

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

        int serialNumberInt = Integer.parseInt(serialNumber);

        // Validate that car is available
        Optional<Car> carToBeUpdated = carRepository.getCarBySerial(serialNumberInt);
        if (carToBeUpdated.isEmpty())
            throw new CarNotFoundException(String.format("Car with serial number %d could not be found.", serialNumberInt));

        // Map front-end DTO object to entity
        Car carUpdate = carMapper.toEntity(carUpdateDTO);

        // Validate that the client did not change the serial number
        carUpdate.setSerialNumber(carToBeUpdated.get().getSerialNumber());

        if(carUpdate.getEngine() != null) {
            euroLevelService.setEngineStandards(carUpdate);
        }

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

        if (carRepository.getCarBySerial(serialNumberInt).isPresent()) {
            throw new CarDeletionException(String.format("Cannot delete car with serial number %d.", serialNumberInt));
        }

        return carMapper.toDTO(carToBeDeleted.get());
    }

}
