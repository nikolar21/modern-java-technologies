package com.project.mjt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTOResponse {

    String racerEgn;

    String f1Team;

    Date startDateOfFile;

    Date endDateOfFile;

    RacerDTO racer;


}
