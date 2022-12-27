package com.hydrogarden.server;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="circuit_schedule")
public class CircuitSchedule extends AbstractEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private Circuit circuit;

    private Date startDate;
    private Date endDate;
    private int frequencyDays;
    private int wateringTime;
    private boolean enabled;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
