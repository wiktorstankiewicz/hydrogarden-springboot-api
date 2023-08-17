package com.hydrogarden.server.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "circuit")
    @JsonIgnore
    private List<CircuitSchedule> circuitSchedule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "circuit")
    @JsonIgnore
    private List<GeneratedTask> generatedTasks;




}

