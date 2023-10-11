package com.hydrogarden.server.domain.mappers;

import com.hydrogarden.server.domain.dto.CircuitDTO;
import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import com.hydrogarden.server.domain.dto.UserDTO;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.entities.User;

import java.util.stream.Collectors;

public class CircuitScheduleMapper {

    public static CircuitSchedule fromDTO(CircuitScheduleDTO circuitScheduleDTO){
        User user = UserMapper.fromDTO(circuitScheduleDTO.getCircuit().getUser());
        Circuit circuit = CircuitMapper.fromDTO(circuitScheduleDTO.getCircuit(), user);
        return fromDTO(circuitScheduleDTO, circuit);
    }

    public static CircuitScheduleDTO fromEntity(CircuitSchedule circuitSchedule){
        UserDTO userDTO = UserMapper.fromEntity(circuitSchedule.getCircuit().getUser());
        CircuitDTO circuitDTO = CircuitMapper.fromEntity(circuitSchedule.getCircuit(),userDTO);
        return fromEntity(circuitSchedule,circuitDTO);
    }

     static CircuitSchedule fromDTO(CircuitScheduleDTO circuitScheduleDTO, Circuit circuit) {
        CircuitSchedule circuitSchedule = new CircuitSchedule();
        circuitSchedule.setId(circuitScheduleDTO.getId());
        circuitSchedule.setStartTime(circuitScheduleDTO.getStartTime());
        circuitSchedule.setStartDate(circuitScheduleDTO.getStartDate());
        circuitSchedule.setEndDate(circuitScheduleDTO.getEndDate());
        circuitSchedule.setFrequencyDays(circuitScheduleDTO.getFrequencyDays());
        circuitSchedule.setWateringTime(circuitScheduleDTO.getWateringTime());
        circuitSchedule.setDeactivated(circuitScheduleDTO.getDeactivated());
        circuitSchedule.setCircuit(circuit);
        circuitSchedule.setGeneratedTasks(circuitScheduleDTO.getGeneratedTasks().stream()
                .map(generatedTaskDTO -> GeneratedTaskMapper.fromDTO(generatedTaskDTO, circuitSchedule))
                .collect(Collectors.toList()));
        return circuitSchedule;
    }

     static CircuitScheduleDTO fromEntity(CircuitSchedule circuitSchedule, CircuitDTO circuitDTO) {
        CircuitScheduleDTO circuitScheduleDTO = new CircuitScheduleDTO();
        circuitScheduleDTO.setId(circuitSchedule.getId());
        circuitScheduleDTO.setStartTime(circuitSchedule.getStartTime());
        circuitScheduleDTO.setStartDate(circuitSchedule.getStartDate());
        circuitScheduleDTO.setEndDate(circuitSchedule.getEndDate());
        circuitScheduleDTO.setFrequencyDays(circuitSchedule.getFrequencyDays());
        circuitScheduleDTO.setWateringTime(circuitSchedule.getWateringTime());
        circuitScheduleDTO.setDeactivated(circuitSchedule.isDeactivated());
        circuitScheduleDTO.setCircuit(circuitDTO);
        circuitScheduleDTO.setGeneratedTasks(circuitSchedule.getGeneratedTasks().stream()
                .map(generatedTask -> GeneratedTaskMapper.fromEntity(generatedTask, circuitScheduleDTO))
                .collect(Collectors.toList()));
        return circuitScheduleDTO;
    }

}
