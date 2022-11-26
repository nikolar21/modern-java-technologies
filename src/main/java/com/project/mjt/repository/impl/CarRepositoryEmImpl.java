package com.project.mjt.repository.impl;

import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.project.mjt.repository.constrains.NamedQueryConstrains.FIND_ALL_CARS;

@Repository
public class CarRepositoryEmImpl implements CarRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Car> getCars() {
        return em.createNamedQuery(FIND_ALL_CARS, Car.class).getResultList();
    }

    @Override
    public Optional<Car> getCarBySerial(int serialNumber) {
        return Optional.ofNullable(
                em.createQuery("SELECT c FROM Car c where c.serialNumber = :pSerialNumber", Car.class)
                        .setParameter("pSerialNumber", serialNumber).getSingleResult());
    }

    @Override
    public void createCar(Car car) {
        em.persist(car);
    }

    @Override
    @Transactional
    public void updateCar(int serial, Car carUpdate) throws CarNotFoundException {
        Car carBySerial = getCarBySerial(serial)
                .orElseThrow(() ->
         new CarNotFoundException(String.format("Car with serial number %d could not be found.", serial)));

        carBySerial.setBrand(carUpdate.getBrand());
        carBySerial.setEngine(carUpdate.getEngine());
        carBySerial.setYear(carUpdate.getYear());
        carBySerial.setModel(carUpdate.getModel());
        createCar(carBySerial);
    }

    @Override
    public void deleteCar(Car car) {

    }

    @Override
    public int generateSerialNumber() {
        return 0;
    }

    @Override
    public void saveData() throws IOException {

    }
}
