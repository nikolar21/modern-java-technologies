package com.project.mjt.services.impl;

import com.project.mjt.models.Car;
import com.project.mjt.services.SerialNumberGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class SerialNumberGeneratorImpl implements SerialNumberGenerator {

    @PersistenceContext
    private EntityManager em;

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

    @Override
    public int generateSerialNumber() {
        return ++serialNumber;
    }

}
