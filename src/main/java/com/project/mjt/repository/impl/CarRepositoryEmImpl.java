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
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.project.mjt.repository.constrains.NamedQueryConstrains.FIND_ALL_CARS;

@Repository("CarRepositoryEM")
public class CarRepositoryEmImpl implements CarRepository {

    private int serialNumber;

    @PostConstruct
    private void postConstruct() {
        try {
            Car singleResult = em.createQuery("SELECT c FROM Car c ORDER BY c.serialNumber desc", Car.class).setMaxResults(1).getSingleResult();
            serialNumber = singleResult.getSerialNumber() + 1;
        } catch (NoResultException e) {
            serialNumber = 1000000;
        }
    }

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

    @Override
    public int generateSerialNumber() {
        return ++serialNumber;
    }

    @Override
    public void saveData() throws IOException {

    }
}
