/*
 * Copyright (c) 2022 VMware, Inc. All rights reserved. VMware Confidential
 */
package com.project.mjt.models;

import java.io.Serializable;

import javax.persistence.*;

import com.project.mjt.services.EuroLevelService;

import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.project.mjt.repository.constrains.NamedQueryConstrains.FIND_ALL_CARS;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NamedQueries({
        @NamedQuery(name = FIND_ALL_CARS, query = "SELECT c FROM Car c"),
})
public class Car extends Vehicle implements Serializable {

    @Column(nullable = false, updatable = false, unique = true)
    private Integer serialNumber;

    @ManyToOne
    private Engine engine;
}
