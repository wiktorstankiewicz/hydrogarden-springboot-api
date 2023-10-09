package com.hydrogarden.server.domain.entities;

import com.hydrogarden.server.domain.dto.CircuitScheduleDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Time;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

    public CircuitSchedule(CircuitScheduleDto dto) {
        this.startTime = dto.getStartTime();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.frequencyDays = dto.getFrequencyDays();
        this.wateringTime = dto.getWateringTime();
        this.deactivated = dto.isDeactivated();
        this.circuit = Circuit.fromCircuitDto(dto.getCircuitDto());
    }
}
