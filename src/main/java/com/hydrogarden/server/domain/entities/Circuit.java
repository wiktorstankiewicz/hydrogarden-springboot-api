package com.hydrogarden.server.domain.entities;

import com.hydrogarden.server.domain.dto.CircuitDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Table(name = "circuit")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Circuit extends AbstractEntity {

    private int code;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "circuit", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private CircuitSchedule circuitSchedule;
}

