package com.hydrogarden.server.domain.dto;


import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.GeneratedTask;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class GeneratedTaskDto {

    private long id;

    @NotNull
    @NotBlank
    private LocalDateTime dateTime;


    private boolean mode;


    private long userId;

    @NotNull
    private CircuitDto circuit;

    public GeneratedTaskDto(GeneratedTask generatedTask){
        dateTime = generatedTask.getDatetime();
        mode = generatedTask.isMode();
        userId = generatedTask.getUser().getId();
        circuit = new CircuitDto(generatedTask.getCircuit());
        id = generatedTask.getId();
    }
}
