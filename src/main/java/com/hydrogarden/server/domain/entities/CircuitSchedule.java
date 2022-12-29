package com.hydrogarden.server.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "circuit_schedule")
public class CircuitSchedule extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Circuit circuit;

    private Date startDate;
    private Date endDate;
    private int frequencyDays;
    private int wateringTime;
    private boolean enabled;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public CircuitSchedule(Circuit circuit, Date startDate, Date endDate, int frequencyDays, int wateringTime, boolean enabled, User user) {
        this.circuit = circuit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequencyDays = frequencyDays;
        this.wateringTime = wateringTime;
        this.enabled = enabled;
        this.user = user;
    }

    public CircuitSchedule() {

    }
}
