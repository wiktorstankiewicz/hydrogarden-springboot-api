package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CircuitDTO {
    @NotNull
    private Integer id;
    @Positive
    @NotNull
    private Integer circuitCode;
    @NotNull
    @NotBlank
    private String circuitName;
    @JsonProperty("circuitSchedule")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CircuitScheduleDTO circuitSchedule;

    private Integer circuitState;

    @NotNull
    @Valid
    private UserDTO user;

    public CircuitDTO(Circuit circuit, UserDTO userDTO, CircuitScheduleDTO circuitScheduleDTO){
        id = circuit.getId();
        circuitCode = circuit.getCode();
        circuitName = circuit.getName();
        if(!circuit.getCircuitSchedules().isEmpty()){
            circuitSchedule = circuitScheduleDTO;
        }else{
            circuitSchedule = null;
        }
        circuitState = circuit.getCircuitState();
        user = userDTO;
    }

    public CircuitDTO(Integer id,
                      Integer circuitCode,
                      String circuitName,
                      CircuitScheduleDTO circuitSchedule,
                      Integer circuitState,
                      UserDTO user) {
        this.id = id;
        this.circuitCode = circuitCode;
        this.circuitName = circuitName;
        this.circuitSchedule = circuitSchedule;
        this.circuitState = circuitState;
        this.user = user;
    }
}
