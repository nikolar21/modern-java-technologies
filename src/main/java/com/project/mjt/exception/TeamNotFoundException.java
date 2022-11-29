package com.project.mjt.exception;

public class TeamNotFoundException extends Exception{

    public TeamNotFoundException() {
        super("Team was not found");
    }
}

