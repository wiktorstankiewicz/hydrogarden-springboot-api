package com.hydrogarden.server.domain.entities;

import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.*;
import java.util.List;

@Table(name = "circuit_schedule")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CircuitSchedule extends AbstractEntity {

    private LocalTime startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private int frequencyDays;
    private int wateringTime;
    private boolean deactivated;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "circuit_schedule_id")
    private List<GeneratedTask> generatedTasks;
}
