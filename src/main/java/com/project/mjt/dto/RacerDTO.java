package com.project.mjt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacerDTO {

    private String firstName;

    private String surName;

    private String lastName;

    private String egn;

    private String placeOfBirth;

}
