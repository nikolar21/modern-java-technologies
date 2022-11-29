package com.project.mjt.controllers;

import com.project.mjt.dto.RacerDTO;
import com.project.mjt.dto.RacerDTOResponse;
import com.project.mjt.exception.RacerNotFoundException;
import com.project.mjt.models.Racer;
import com.project.mjt.services.RacerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/racer")
public class RacerController {

    @Autowired
    RacerService racerService;

    @PostMapping()
    public void createRacer(@RequestBody RacerDTO racerDto) {
        racerService.createRacer(racerDto);
    }

    @GetMapping
    public List<RacerDTOResponse> getRacers(){
       return racerService.getRacers();
    }

    @DeleteMapping
    public void deleteRacer(String egn){
        racerService.deleteRacer(egn);
    }

    @PutMapping
    public void addCars(@RequestBody List<Integer> carsSerialNumber,@RequestParam String egn) throws RacerNotFoundException {
        racerService.addCars(carsSerialNumber,egn);
    }


}
