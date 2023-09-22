package com.hydrogarden.server.controllers.requestResponseEntities;

import com.hydrogarden.server.domain.dto.GeneratedTaskDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Data
public class DeviceTaskResponseEntity {
    @Min(1)
    @Max(100)
    private int circuitCode;
    private long generatedTaskId;
    private boolean mode;

    public static DeviceTaskResponseEntity fromGeneratedTaskDto(@Validated GeneratedTaskDto generatedTaskDto) {
        int circuitCode = generatedTaskDto.getCircuit().getCircuitCode();
        boolean mode = generatedTaskDto.isMode();
        long id = generatedTaskDto.getId();
        return new DeviceTaskResponseEntity(circuitCode, id ,mode);
    }
}
