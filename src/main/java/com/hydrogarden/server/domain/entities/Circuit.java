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

    public static final int CIRCUIT_STATE_OFF = 0;

    public static final int CIRCUIT_STATE_UNKNOWN = -1;

    public static final int CIRCUIT_STATE_ON = 1;

    private int code;

    private String name;

    @Min(-1)
    @Max(1)
    @Column(nullable = true)
    private Integer circuitState;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "circuit", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CircuitSchedule> circuitSchedules;


    public static Circuit fromCircuitDto(CircuitDTO circuitDto) {
        Circuit circuit = new Circuit();
        circuit.setId(circuitDto.getId());
        circuit.setCode(circuitDto.getCircuitCode());
        circuit.setName(circuitDto.getCircuitName());
        circuit.setCircuitState(circuitDto.getCircuitState());
        circuit.setUser(User.fromUserDTO(circuitDto.getUser()));
        return circuit;
    }
}

