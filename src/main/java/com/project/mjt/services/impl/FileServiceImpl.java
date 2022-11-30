package com.project.mjt.services.impl;

import com.project.mjt.dto.FileDTO;
import com.project.mjt.dto.FileDTOResponse;
import com.project.mjt.exception.TeamNotFoundException;
import com.project.mjt.models.F1Team;
import com.project.mjt.models.File;
import com.project.mjt.models.Racer;
import com.project.mjt.repositoryJPA.FileRepository;
import com.project.mjt.repositoryJPA.RacerRepository;
import com.project.mjt.services.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RacerRepository racerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void createFile(FileDTO file) throws TeamNotFoundException {
        File fileEntity = modelMapper.map(file, File.class);
        fileEntity.setTeam(F1Team.getTeamByValue(file.getF1Team()));
        Racer racer = racerRepository.findByEgn(file.getRacerEgn()).orElse(null);
        fileEntity.setRacer(racer);
        fileRepository.save(fileEntity);
    }

    @Override
    public void deleteFile(Long id) {

    }

    @Override
    public FileDTOResponse getFileByRacerEgn(String egn) throws FileNotFoundException {
        File file = fileRepository.getFileByRacerEgn(egn).orElseThrow(FileNotFoundException::new);
        return modelMapper.map(file, FileDTOResponse.class);
    }
}
