package com.hydrogarden.server.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydrogarden.server.domain.entities.GeneratedTask;
import jakarta.persistence.Column;
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
    private LocalDateTime dateTime;

    @NotNull
    private Boolean mode;

    @NotNull
    private Boolean done;

    @NotNull
    @JsonProperty("user")
    private UserDTO userDTO;

    @NotNull
    @Valid
    @JsonProperty("circuit")
    private CircuitDTO circuitDTO;

    public GeneratedTaskDTO(GeneratedTask generatedTask){
        dateTime = generatedTask.getDatetime();
        mode = generatedTask.isMode();
        userDTO = new UserDTO(generatedTask.getUser());
        circuitDTO = new CircuitDTO(generatedTask.getCircuit(), new UserDTO(generatedTask.getCircuit().getUser()),new CircuitScheduleDTO(generatedTask.getCircuitSchedule(),new UserDTO(generatedTask.getUser())));
        done = generatedTask.isDone();
        id = generatedTask.getId();
    }

    public GeneratedTaskDTO(Integer id,
                            LocalDateTime dateTime,
                            Boolean mode,
                            Boolean done,
                            UserDTO userDTO,
                            CircuitDTO circuitDTO) {
        this.id = id;
        this.dateTime = dateTime;
        this.mode = mode;
        this.done = done;
        this.userDTO = userDTO;
        this.circuitDTO = circuitDTO;
    }
}
