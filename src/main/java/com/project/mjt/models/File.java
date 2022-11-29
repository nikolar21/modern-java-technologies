package com.project.mjt.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class File extends BasicEntity{

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "racer_id", referencedColumnName = "id")
    private Racer racer;

    @Column
    @Enumerated(EnumType.STRING)
    private F1Team team;

    @Column
    private Timestamp startDateOfFile;

    @Column
    private Timestamp endDateOfFile;
}
