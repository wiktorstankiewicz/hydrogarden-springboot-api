package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.*;

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

    @ManyToOne
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

}
