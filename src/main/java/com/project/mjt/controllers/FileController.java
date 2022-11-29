package com.project.mjt.controllers;

import com.project.mjt.dto.FileDTO;
import com.project.mjt.dto.FileDTOResponse;
import com.project.mjt.exception.TeamNotFoundException;
import com.project.mjt.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping()
    public void createFile(@RequestBody FileDTO fileDTO) throws TeamNotFoundException {
        fileService.createFile(fileDTO);
    }
    @GetMapping
    public FileDTOResponse getFileByRacerEgn(@RequestParam String egn) throws FileNotFoundException {
       return fileService.getFileByRacerEgn(egn);
    }


    @DeleteMapping
    public void deleteFile(Long id){
        fileService.deleteFile(id);
    }
}
