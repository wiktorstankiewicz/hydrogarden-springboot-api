package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "generated_task")
public class GeneratedTask extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Circuit circuit;
    private Date datetime;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    private boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
