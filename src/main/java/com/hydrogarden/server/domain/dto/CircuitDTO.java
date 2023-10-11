package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Integer code;
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty("circuitSchedule")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CircuitScheduleDTO circuitSchedule;

    @NotNull
    @Valid
    private UserDTO user;


}
