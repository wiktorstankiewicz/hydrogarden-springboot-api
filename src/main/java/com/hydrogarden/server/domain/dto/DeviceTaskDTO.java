package com.hydrogarden.server.domain.dto;

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
    private Integer generatedTaskId;

    @NotNull
    private Boolean mode;

    public static DeviceTaskDTO fromGeneratedTaskDto(GeneratedTaskDTO generatedTaskDto)
    {
        DeviceTaskDTO deviceTaskDTO = new DeviceTaskDTO(
                generatedTaskDto.getCircuitSchedule().getCircuit().getCode(),
                generatedTaskDto.getId(),
                generatedTaskDto.getMode()
        );
        return deviceTaskDTO;
    }
}
