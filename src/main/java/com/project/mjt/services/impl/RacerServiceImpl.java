package com.project.mjt.services.impl;

import com.project.mjt.dto.RacerDTO;
import com.project.mjt.dto.RacerDTOResponse;
import com.project.mjt.exception.RacerNotFoundException;
import com.project.mjt.models.Car;
import com.project.mjt.models.Racer;
import com.project.mjt.repositoryJPA.CarRepositoryJPA;
import com.project.mjt.repositoryJPA.RacerRepository;
import com.project.mjt.services.RacerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RacerServiceImpl implements RacerService {

    @Autowired
    RacerRepository racerRepository;

    @Autowired
    CarRepositoryJPA carRepositoryJPA;

    @Autowired
    ModelMapper modelMapper;



    @Override
    public void createRacer(RacerDTO racer) {
        Racer racerEntity = modelMapper.map(racer, Racer.class);
        racerRepository.save(racerEntity);
    }

    @Override
    @Transactional
    public void deleteRacer(String egn) {
        racerRepository.deleteByEgn(egn);
    }

    @Override
    @Transactional
    public List<RacerDTOResponse> getRacers() {
        return racerRepository.findAll().stream().map(it -> modelMapper.map(it,RacerDTOResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCars(List<Integer> carsSerialNumber, String egn) throws RacerNotFoundException {
        Set<Car> cars = carRepositoryJPA.getCarsBySerial(carsSerialNumber);
        Racer racer = racerRepository.findByEgn(egn).orElseThrow(RacerNotFoundException::new);
        cars.forEach(it -> it.getDrivers().add(racer));
        racer.setCars(cars);
        racerRepository.saveAndFlush(racer);
    }
}
