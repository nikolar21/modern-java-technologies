package com.project.mjt.repositoryJPA;

import com.project.mjt.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepositoryJPA extends JpaRepository<Car,Long> {

    @Query("SELECT c FROM Car c where c.serialNumber = :pSerialNumber")
    Optional<Car> getCarBySerial(@Param("pSerialNumber") Integer serialNumber);

}
