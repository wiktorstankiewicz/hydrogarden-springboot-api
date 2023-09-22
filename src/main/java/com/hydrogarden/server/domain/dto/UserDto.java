package com.hydrogarden.server.domain.dto;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto{

    private long id;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    private Collection<? extends GrantedAuthority> authorities;
    @NotNull
    private List<CircuitDto> circuits;


    public UserDto(User user){
        if(user == null){
            throw new IllegalArgumentException();
        }
        this.id=user.getId();
        this.username=user.getUsername();
        this.authorities=user.getAuthorities();
        this.circuits=user.getCircuits().stream().map(CircuitDto::new).collect(Collectors.toList());
    }
}
