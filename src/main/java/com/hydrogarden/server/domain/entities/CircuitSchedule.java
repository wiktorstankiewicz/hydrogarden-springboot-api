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
    private boolean deactivated;

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getFrequencyDays() {
        return frequencyDays;
    }

    public void setFrequencyDays(int frequencyDays) {
        this.frequencyDays = frequencyDays;
    }

    public int getWateringTime() {
        return wateringTime;
    }

    public void setWateringTime(int wateringTime) {
        this.wateringTime = wateringTime;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean enabled) {
        this.deactivated = enabled;
    }
}
