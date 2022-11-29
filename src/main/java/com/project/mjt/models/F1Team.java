package com.project.mjt.models;

import com.project.mjt.exception.TeamNotFoundException;

import java.util.Arrays;
import java.util.Optional;

public enum F1Team {
    MERCEDES("Mercedes"),
    RED_BULL("Red bull"),
    ASTON_MARTIN("Aston Margin"),
    ALFA_ROMEO("Alfa Romeo"),
    MCLAREN("Mclaren");

    public final String value;

    F1Team(String value) {
        this.value = value;
    }

    public static F1Team getTeamByValue(String value1) throws TeamNotFoundException {
            return Arrays.stream(F1Team.values()).filter(it -> it.value.equals(value1)).findFirst().orElseThrow(TeamNotFoundException::new);
    }
}
