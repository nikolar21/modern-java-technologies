package com.project.mjt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Racer extends BasicEntity {

    @Column(nullable = false, updatable = false)
    private String firstName;

    @Column(nullable = false, updatable = false)
    private String surName;

    @Column(nullable = false, updatable = false)
    private String lastName;

    @Column(nullable = false, updatable = false, unique = true)
    private String egn;

    @Column(nullable = false, updatable = false)
    private String placeOfBirth;

    @ManyToMany(mappedBy = "drivers", fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Car> cars;


}
