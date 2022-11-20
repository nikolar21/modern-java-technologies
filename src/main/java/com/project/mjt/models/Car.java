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

import com.project.mjt.services.EuroLevelService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Engine implements Serializable {
        private String name;
        private EngineType type;
        EuroLevelService.EuroLevel euroLevel;
    }

    Engine engine;
}
