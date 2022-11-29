package com.project.mjt.repositoryJPA;

import com.project.mjt.models.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RacerRepository extends JpaRepository<Racer,Long> {

    @Modifying
    void deleteByEgn(String egn);

    Optional<Racer> findByEgn(String egn);
}
