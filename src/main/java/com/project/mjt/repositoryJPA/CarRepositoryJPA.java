package com.project.mjt.repositoryJPA;

import com.project.mjt.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepositoryJPA extends JpaRepository<Car,Long> {
}
