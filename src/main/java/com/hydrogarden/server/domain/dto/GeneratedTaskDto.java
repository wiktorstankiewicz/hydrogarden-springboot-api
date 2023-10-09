package com.hydrogarden.server.domain.dto;


import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.GeneratedTask;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class GeneratedTaskDto {


    @NotNull
    private Integer id;

    @NotNull
    @NotBlank
    private LocalDateTime dateTime;

    @NotNull
    private Boolean mode;


    @NotNull
    private Long userId;

    @NotNull
    @Valid
    private CircuitDto circuit;

    public GeneratedTaskDto(GeneratedTask generatedTask){
        dateTime = generatedTask.getDatetime();
        mode = generatedTask.isMode();
        userId = generatedTask.getUser().getId();
        circuit = new CircuitDto(generatedTask.getCircuit());
        id = generatedTask.getId();
    }
}
