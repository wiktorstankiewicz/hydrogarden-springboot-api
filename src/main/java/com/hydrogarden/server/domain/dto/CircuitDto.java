package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CircuitDto {

    private long id;
    @Positive
    private int code;
    @NotNull
    @NotBlank
    private String name;
    private long userId;
    @NotNull
    @JsonProperty("circuitSchedules")
    private List<CircuitScheduleDto> circuitScheduleDtoList;


    public CircuitDto(Circuit circuit){
        id = circuit.getId();
        code = circuit.getCode();
        name = circuit.getName();
        userId = circuit.getUser().getId();
        circuitScheduleDtoList = circuit.getCircuitSchedules().stream().map(CircuitScheduleDto::new).collect(Collectors.toList());
    }

}
