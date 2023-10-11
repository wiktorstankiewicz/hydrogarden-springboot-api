package com.hydrogarden.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hydrogarden.server.domain.entities.Role;
import com.hydrogarden.server.domain.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {

    private long id;
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @JsonIgnore
    private String password;

    @NotNull
    private Role role;
    @NotNull
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;
    @NotNull
    private List<CircuitDTO> circuits;

    @NotNull
    private List<GeneratedTaskDTO> generatedTasks;

    public UserDTO(User user){
        if(user == null){
            throw new IllegalArgumentException();
        }
        this.id=user.getId();
        this.username=user.getUsername();
        this.authorities=user.getAuthorities();
        this.circuits=user.getCircuits().stream().map(circuit -> new CircuitDTO(circuit,this,new CircuitScheduleDTO(circuit.getCircuitSchedules().get(0),this))).collect(Collectors.toList());
    }

    public UserDTO(long id,
                   String username,
                   String password,
                   Role role,
                   Collection<? extends GrantedAuthority> authorities,
                   List<CircuitDTO> circuits,
                   List<GeneratedTaskDTO> generatedTasks) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.authorities = authorities;
        this.circuits = circuits;
        this.generatedTasks = generatedTasks;
    }
}
