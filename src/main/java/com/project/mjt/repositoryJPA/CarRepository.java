package com.project.mjt.repositoryJPA;

import com.project.mjt.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CarRepositoryJPA")
public interface CarRepository extends JpaRepository<Car,Long> {
}
