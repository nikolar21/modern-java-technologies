package com.project.mjt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    String racerEgn;

    String f1Team;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date startDateOfFile;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date endDateOfFile;


}
