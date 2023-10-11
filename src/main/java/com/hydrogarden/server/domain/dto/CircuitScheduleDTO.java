package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CircuitScheduleDTO {

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
    @JsonIgnore
    @Valid
    @NotNull
    private CircuitDTO circuitDto;

    public CircuitScheduleDTO(CircuitSchedule cs, UserDTO userDTO) {
        startTime = cs.getStartTime();
        startDate = cs.getStartDate();
        endDate = cs.getEndDate();
        frequencyDays = cs.getFrequencyDays();
        wateringTime = cs.getWateringTime();
        deactivated = cs.isDeactivated();
        id = cs.getId();
        circuitDto = new CircuitDTO(cs.getCircuit(),userDTO,this);
    }

    public CircuitScheduleDTO(Integer id,
                              LocalTime startTime,
                              LocalDate startDate,
                              LocalDate endDate,
                              Integer frequencyDays,
                              Integer wateringTime,
                              Boolean deactivated,
                              CircuitDTO circuitDto) {
        this.id = id;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequencyDays = frequencyDays;
        this.wateringTime = wateringTime;
        this.deactivated = deactivated;
        this.circuitDto = circuitDto;
    }
}
