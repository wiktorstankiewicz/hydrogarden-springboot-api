package com.hydrogarden.server.controllers.requestResponseEntities;

import com.hydrogarden.server.domain.dto.GeneratedTaskDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeviceTaskDTO {
    @Min(1)
    @Max(100)
    @NotNull
    private Integer circuitCode;

    @NotNull
    private Long generatedTaskId;

    @NotNull
    private Boolean mode;

    public static DeviceTaskDTO fromGeneratedTaskDto(@Valid GeneratedTaskDto generatedTaskDto) {
        int circuitCode = generatedTaskDto.getCircuit().getCircuitCode();
        boolean mode = generatedTaskDto.isMode();
        long id = generatedTaskDto.getId();
        return new DeviceTaskDTO(circuitCode, id ,mode);
    }
}
