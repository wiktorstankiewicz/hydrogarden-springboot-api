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





}
