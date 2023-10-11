package com.hydrogarden.server.domain.mappers;

import com.hydrogarden.server.domain.dto.CircuitDTO;
import com.hydrogarden.server.domain.dto.UserDTO;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import jakarta.validation.Valid;

public class CircuitMapper {

    public static Circuit fromDTO(@Valid CircuitDTO circuitDTO, @Valid User user){
        Circuit circuit =  new Circuit();
        circuit.setId(circuitDTO.getId());
        circuit.setCode(circuitDTO.getCode());
        circuit.setName(circuitDTO.getName());
        circuit.setUser(user);
        circuit.setCircuitSchedule(CircuitScheduleMapper.fromDTO( circuitDTO.getCircuitSchedule(),circuit));
        return circuit;
    }

    public static CircuitDTO fromEntity(Circuit circuit, UserDTO userDTO){
        CircuitDTO circuitDTO = new CircuitDTO();
        circuitDTO.setId(circuit.getId());
        circuitDTO.setCode(circuit.getCode());
        circuitDTO.setName(circuit.getName());
        circuitDTO.setUser(userDTO);
        circuitDTO.setCircuitSchedule(CircuitScheduleMapper.fromEntity(circuit.getCircuitSchedule(),circuitDTO));
        return circuitDTO;
    }
}
