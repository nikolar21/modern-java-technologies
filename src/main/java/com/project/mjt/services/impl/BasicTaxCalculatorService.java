/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.services.impl;

import java.time.Year;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.project.mjt.models.Car;
import com.project.mjt.services.TaxCalculatorService;

@Service
public class BasicTaxCalculatorService implements TaxCalculatorService {

    @Override
    public void setTaxBracket(Car car) {
        float carPrice = car.getPrice();
        int manufactureYear = car.getYear();
        int currentYear = Year.now().getValue();

        if (carPrice <= 0 || manufactureYear <= 1970) {
            car.setExpectedTaxRate(0);
            return;
        }

        float expectedTax = (float) (carPrice * 0.01 + 500 / (Math.floor((currentYear - manufactureYear) / 5)));

        car.setExpectedTaxRate(expectedTax);
    }
}
