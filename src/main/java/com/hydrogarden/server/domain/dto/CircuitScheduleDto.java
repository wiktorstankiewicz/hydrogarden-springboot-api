package com.hydrogarden.server.domain.dto;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CircuitScheduleDto {
    @NotNull

    private LocalTime startTime;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    @Positive
    @Max(365)
    private int frequencyDays;

    @Positive
    @Min(5)
    @Max(24*60*60)
    private int wateringTime;
    private boolean deactivated;
    private long circuitId;

    public CircuitScheduleDto(CircuitSchedule cs) {
        startTime = cs.getStartTime();
        startDate = cs.getStartDate();
        endDate = cs.getEndDate();
        frequencyDays = cs.getFrequencyDays();
        wateringTime = cs.getWateringTime();
        deactivated = cs.isDeactivated();
        circuitId = cs.getCircuit().getId();
    }
}
