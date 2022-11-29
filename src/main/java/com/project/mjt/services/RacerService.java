package com.project.mjt.services;

import com.project.mjt.dto.RacerDTO;
import com.project.mjt.dto.RacerDTOResponse;
import com.project.mjt.exception.RacerNotFoundException;
import com.project.mjt.models.Racer;

import java.util.List;

public interface RacerService {

    void createRacer(RacerDTO racer);

    void deleteRacer(String egn);

    List<RacerDTOResponse> getRacers();

    void addCars(List<Integer> carsSerialNumber, String egn) throws RacerNotFoundException;

}
