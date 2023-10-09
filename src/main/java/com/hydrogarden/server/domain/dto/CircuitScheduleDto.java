package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class CircuitScheduleDto {

    @NotNull
    private Integer id;
    @NotNull
    @JsonFormat(pattern = "hh:mm")
    private LocalTime startTime;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")

    private LocalDate startDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Positive
    @Max(365)
    @NotNull
    private Integer frequencyDays;

    @Positive
    @Min(5)
    @Max(24*60*60)
    @NotNull
    private Integer wateringTime;
    @NotNull
    private Boolean deactivated;
    @Valid
    @NotNull
    private CircuitDto circuitDto;

    public CircuitScheduleDto(CircuitSchedule cs) {
        startTime = cs.getStartTime();
        startDate = cs.getStartDate();
        endDate = cs.getEndDate();
        frequencyDays = cs.getFrequencyDays();
        wateringTime = cs.getWateringTime();
        deactivated = cs.isDeactivated();
        id = cs.getId();
        circuitDto = new CircuitDto(cs.getCircuit());
    }

}
