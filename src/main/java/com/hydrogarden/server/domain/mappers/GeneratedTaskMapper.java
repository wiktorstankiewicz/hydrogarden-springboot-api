package com.hydrogarden.server.domain.mappers;

import com.hydrogarden.server.domain.dto.CircuitDTO;
import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import com.hydrogarden.server.domain.dto.GeneratedTaskDTO;
import com.hydrogarden.server.domain.dto.UserDTO;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.entities.GeneratedTask;
import com.hydrogarden.server.domain.entities.User;

public class GeneratedTaskMapper {


    public static GeneratedTask fromDTO(GeneratedTaskDTO generatedTaskDTO){
        User user = UserMapper.fromDTO(generatedTaskDTO.getCircuitSchedule().getCircuit().getUser());
        Circuit circuit = CircuitMapper.fromDTO(generatedTaskDTO.getCircuitSchedule().getCircuit(),user);
        CircuitSchedule circuitSchedule = CircuitScheduleMapper.fromDTO(generatedTaskDTO.getCircuitSchedule(),circuit);
        return fromDTO(generatedTaskDTO,circuitSchedule);
    }

    public static GeneratedTaskDTO fromEntity(GeneratedTask generatedTask){
        UserDTO userDTO = UserMapper.fromEntity(generatedTask.getCircuitSchedule().getCircuit().getUser());
        CircuitDTO circuitDTO = CircuitMapper.fromEntity(generatedTask.getCircuitSchedule().getCircuit(),userDTO);
        CircuitScheduleDTO circuitScheduleDTO = CircuitScheduleMapper.fromEntity(generatedTask.getCircuitSchedule(),circuitDTO);
        return fromEntity(generatedTask,circuitScheduleDTO);
    }

     static GeneratedTask fromDTO(GeneratedTaskDTO generatedTaskDTO, CircuitSchedule circuitSchedule){
        GeneratedTask generatedTask = new GeneratedTask();
        generatedTask.setId(generatedTaskDTO.getId());
        generatedTask.setDatetime(generatedTaskDTO.getDatetime());
        generatedTask.setMode(generatedTaskDTO.getMode());
        generatedTask.setDone(generatedTaskDTO.getDone());
        generatedTask.setCircuitSchedule(circuitSchedule);
        return generatedTask;
    }

     static GeneratedTaskDTO fromEntity(GeneratedTask generatedTask, CircuitScheduleDTO circuitScheduleDTO){
        GeneratedTaskDTO generatedTaskDTO = new GeneratedTaskDTO();
        generatedTaskDTO.setId(generatedTask.getId());
        generatedTaskDTO.setDatetime(generatedTask.getDatetime());
        generatedTaskDTO.setMode(generatedTask.isMode());
        generatedTaskDTO.setDone(generatedTask.isDone());
        generatedTaskDTO.setCircuitSchedule(circuitScheduleDTO);
        return generatedTaskDTO;
    }
}
