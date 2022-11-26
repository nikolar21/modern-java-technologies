/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.mjt.models.Engine;
import org.springframework.stereotype.Service;

import com.project.mjt.models.Car;
import com.project.mjt.models.EngineType;
import com.project.mjt.services.EuroLevelService;

@Service
public class EuroLevelImpl implements EuroLevelService {

    private static final Pattern dieselPattern =
            Pattern.compile("^([0-9]\\.[0-9]*d)|(.*?(TDI|HDI|CDI|DTI|BlueHDI|Bluemotion|DTHI|GTD|d|D).*)$");
    private static final Pattern electricPattern = Pattern.compile("^.*kWh.*$");
    private static final Pattern hybridPattern = Pattern.compile("^.*hybrid.*$");

    @Override
    public void setEngineStandards(Car car) {

        setEngineType(car.getEngine());
        setEuroLevel(car.getEngine(), car.getYear());
    }

    private void setEngineType(Engine engine) {

        if (engine.getName() == null) {
            engine.setType(EngineType.UNKNOWN);
            return;
        }

        if (isEngineType(hybridPattern, engine.getName().toLowerCase())) {
            engine.setType(EngineType.HYBRID);
        } else if (isEngineType(electricPattern, engine.getName())) {
            engine.setType(EngineType.ELECTRIC);
        } else if (isEngineType(dieselPattern, engine.getName())) {
            engine.setType(EngineType.DIESEL);
        } else {
            engine.setType(EngineType.PETROL);
        }
    }

    private void setEuroLevel(Engine engine, Integer manufactureYear) {

        EngineType engineType = engine.getType();

        if (manufactureYear == null) {
            engine.setEuroLevel(EuroLevel.UNKNOWN);
            return;
        }

        if (engineType == EngineType.ELECTRIC ||
            engineType == EngineType.UNKNOWN) {
            engine.setEuroLevel(EuroLevel.NONE);
            return;
        }

        if (isBetween(1993, 1997, manufactureYear)) {
            engine.setEuroLevel(EuroLevel.EURO_1);
        } else if (isBetween(1997, 2001, manufactureYear)) {
            engine.setEuroLevel(EuroLevel.EURO_2);
        } else if (isBetween(2001, 2006, manufactureYear)) {
            engine.setEuroLevel(EuroLevel.EURO_3);
        } else if (isBetween(2006, 2011, manufactureYear)) {
            engine.setEuroLevel(EuroLevel.EURO_4);
        } else if (isBetween(2011, 2015, manufactureYear)) {
            engine.setEuroLevel(EuroLevel.EURO_5);
        } else if (isBetween(2015, 2025, manufactureYear)) {
            engine.setEuroLevel(EuroLevel.EURO_6);
        }
    }

    private static boolean isBetween(int startYear, int endYear, int actualYear) {
        return startYear <= actualYear && actualYear < endYear;
    }

    private boolean isEngineType(Pattern pattern, String engineName) {

        Matcher matcher = pattern.matcher(engineName);
        return matcher.find();
    }
}
