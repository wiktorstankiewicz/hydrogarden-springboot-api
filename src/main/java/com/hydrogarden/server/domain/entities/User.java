package com.hydrogarden.server.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hydrogarden.server.domain.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user",
uniqueConstraints = @UniqueConstraint(columnNames = "username")
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'USER'")
    private Role role;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Circuit> circuits;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneratedTask> generatedTasks;

    public static User fromUserDTO(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setId(dto.getId());
        user.setRole(dto.getRole());
        user.setCircuits(dto.getCircuits().stream().map(Circuit::fromCircuitDto).collect(Collectors.toList()));
        user.setGeneratedTasks(dto.getGeneratedTasks().stream().map(GeneratedTask::fromGeneratedTaskDTO).collect(Collectors.toList()));
        return user;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
