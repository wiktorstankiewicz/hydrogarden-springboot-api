package com.hydrogarden.server.domain.mappers;

import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import com.hydrogarden.server.domain.dto.GeneratedTaskDTO;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.entities.GeneratedTask;

public class GeneratedTaskMapper {

    public static GeneratedTask fromDTO(GeneratedTaskDTO generatedTaskDTO, CircuitSchedule circuitSchedule){
        GeneratedTask generatedTask = new GeneratedTask();
        generatedTask.setId(generatedTaskDTO.getId());
        generatedTask.setDatetime(generatedTaskDTO.getDatetime());
        generatedTask.setMode(generatedTaskDTO.getMode());
        generatedTask.setDone(generatedTaskDTO.getDone());
        generatedTask.setCircuitSchedule(circuitSchedule);
        return generatedTask;
    }

    public static GeneratedTaskDTO fromEntity(GeneratedTask generatedTask, CircuitScheduleDTO circuitScheduleDTO){
        GeneratedTaskDTO generatedTaskDTO = new GeneratedTaskDTO();
        generatedTaskDTO.setId(generatedTask.getId());
        generatedTaskDTO.setDatetime(generatedTask.getDatetime());
        generatedTaskDTO.setMode(generatedTask.isMode());
        generatedTaskDTO.setDone(generatedTask.isDone());
        generatedTaskDTO.setCircuitSchedule(circuitScheduleDTO);
        return generatedTaskDTO;
    }
}
