package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydrogarden.server.domain.entities.Circuit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Objects;

@Data
public class CircuitDto {

    private long id;
    @Positive
    private int circuitCode;
    @NotNull
    @NotBlank
    private String circuitName;
    @JsonProperty("circuitSchedule")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CircuitScheduleDto circuitSchedule;


    public CircuitDto(Circuit circuit){
        id = circuit.getId();
        circuitCode = circuit.getCode();
        circuitName = circuit.getName();
        if(!circuit.getCircuitSchedules().isEmpty()){
            circuitSchedule = new CircuitScheduleDto(circuit.getCircuitSchedules().stream().findFirst().get());
        }else{
            circuitSchedule = null;
        }
    }

}
