package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "circuit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Circuit extends AbstractEntity {

    private String code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "circuit")
    private List<CircuitSchedule> circuitSchedule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "circuit")
    private List<GeneratedTask> generatedTasks;




}
