package com.project.mjt.repositoryJPA;

import com.project.mjt.models.Car;
import com.project.mjt.models.File;
import com.project.mjt.models.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {

    @Query("SELECT f FROM File f where f.racer.egn= :pEgn")
    Optional<File> getFileByRacerEgn(@Param("pEgn") String egn);
}
