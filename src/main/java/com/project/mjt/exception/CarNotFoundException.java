/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.exception;

public class CarNotFoundException extends Exception {

    public CarNotFoundException(int serialNumber) {
        String message = "Car with serial number %d was not found.";
        message = String.format(message, serialNumber);
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
