package com.project.mjt.services;

import com.project.mjt.dto.FileDTO;
import com.project.mjt.dto.FileDTOResponse;
import com.project.mjt.exception.TeamNotFoundException;

import java.io.FileNotFoundException;

public interface FileService {

    void createFile(FileDTO file) throws TeamNotFoundException;

    void deleteFile(Long id);

    FileDTOResponse getFileByRacerEgn(String egn) throws FileNotFoundException;

}
