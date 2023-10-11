package com.hydrogarden.server.domain.entities;

import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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



    public static CircuitSchedule fromCircuitScheduleDTO(@Valid CircuitScheduleDTO dto) {
        CircuitSchedule cs =new CircuitSchedule();
        cs.startTime = dto.getStartTime();
        cs.startDate = dto.getStartDate();
        cs.endDate = dto.getEndDate();
        cs.frequencyDays = dto.getFrequencyDays();
        cs.wateringTime = dto.getWateringTime();
        cs.deactivated = dto.getDeactivated();
        cs.circuit = Circuit.fromCircuitDto(dto.getCircuitDto());
        return cs;
    }
}
