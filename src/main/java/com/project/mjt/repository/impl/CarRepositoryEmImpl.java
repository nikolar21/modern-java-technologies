package com.project.mjt.repository.impl;

import com.project.mjt.exception.CarNotFoundException;
import com.project.mjt.models.Car;
import com.project.mjt.repository.CarRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
        Car result;
        try {
            result = em.createQuery("SELECT c FROM Car c where c.serialNumber = :pSerialNumber", Car.class)
                    .setParameter("pSerialNumber", serialNumber).getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        return Optional.ofNullable(result);
    }

    @Override
    @Transactional
    public void createCar(Car car) {
        if (car.getEngine().getId() == null) {
            em.persist(car.getEngine());
        }
        em.persist(car);
    }

    @Override
    @Transactional
    public void updateCar(int serial, Car carUpdate) throws CarNotFoundException {
        Car carBySerial = getCarBySerial(serial)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car with serial number %d could not be found.", serial)));

        carBySerial.setBrand(carUpdate.getBrand());
        carBySerial.setEngine(carUpdate.getEngine());
        carBySerial.setYear(carUpdate.getYear());
        carBySerial.setModel(carUpdate.getModel());
    }

    @Override
    @Transactional
    public void deleteCar(Car car) {
        Optional<Car> carBySerial = getCarBySerial(car.getSerialNumber());
        carBySerial.ifPresent(value -> em.remove(value));
    }

}
