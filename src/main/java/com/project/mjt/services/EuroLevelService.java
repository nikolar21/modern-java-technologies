/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services;

public interface EuroLevelService {

    enum EuroLevel {
        EURO_1,
        EURO_2,
        EURO_3,
        EURO_4,
        EURO_5,
        EURO_6
    }

    EuroLevel getEuroLevel(short manufactureYear);

}
