package com.hydrogarden.server.domain.dto;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UserDto{

    private long id;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private List<Circuit> circuits;


    public UserDto(User user){
        if(user == null){
            throw new IllegalArgumentException();
        }
        this.id=user.getId();
        this.username=user.getUsername();
        this.authorities=user.getAuthorities();
        this.circuits=user.getCircuits();
    }
}
