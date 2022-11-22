/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services;

import com.project.mjt.models.Car;

public interface EuroLevelService {

    void setEngineStandards(Car car);

    enum EuroLevel {
        UNKNOWN,
        NONE,
        EURO_1,
        EURO_2,
        EURO_3,
        EURO_4,
        EURO_5,
        EURO_6
    }
}
