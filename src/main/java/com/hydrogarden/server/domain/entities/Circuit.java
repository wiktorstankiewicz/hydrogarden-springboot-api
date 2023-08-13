package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "circuit")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Circuit extends AbstractEntity {

    private int code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "circuit")
    private List<CircuitSchedule> circuitSchedule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "circuit")
    private List<GeneratedTask> generatedTasks;




}

