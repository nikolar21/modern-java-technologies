/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    Long id;

    @Column(nullable = false, updatable = false, unique = true)
    Integer serialNumber;

    @Column(nullable = false)
    String brand;

    @Column(nullable = false)
    String model;

    @Column(length = 4)
    Integer year;

    String color;

    @Column(length = 17, name = "VIN", unique = true, nullable = true)
    String engineVINNumber;

    String engine;

    // TODO: Decide on 'engine' var type

    //    private enum EngineType {
    //        DIESEL, PETROL, ELECTRIC
    //    }

    // @Value
    // private static class Engine {
    //     private EngineType type;
    //     private int power;
    //     private int torque;
    //     private String name;
    //     private int displacement;
    // }
}
