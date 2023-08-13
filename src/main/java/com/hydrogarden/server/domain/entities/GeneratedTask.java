package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "generated_task")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneratedTask extends AbstractEntity {
    private Date datetime;

    private boolean mode;

    private boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Circuit circuit;


}
