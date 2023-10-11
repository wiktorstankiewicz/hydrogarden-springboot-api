package com.hydrogarden.server.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class GeneratedTaskDTO {


    @NotNull
    private Integer id;

    @NotNull
    @NotBlank
    private LocalDateTime datetime;

    @NotNull
    private Boolean mode;

    @NotNull
    private Boolean done;

    @NotNull
    @Valid
    @JsonProperty("circuitSchedule")
    private CircuitScheduleDTO circuitSchedule;


}
