package com.hydrogarden.server.domain.mappers;

import com.hydrogarden.server.domain.dto.CircuitDTO;
import com.hydrogarden.server.domain.dto.UserDTO;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static User fromDTO(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setCircuits(userDTO.getCircuits().stream()
                .map(circuitDTO -> CircuitMapper.fromDTO(circuitDTO,user))
                .collect(Collectors.toList()));

        return user;
    }

    public static UserDTO fromEntity(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setCircuits(user.getCircuits().stream()
                .map(circuit -> CircuitMapper.fromEntity(circuit,userDTO))
                .collect(Collectors.toList()));
        return userDTO;
    }
}
