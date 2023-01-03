package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "circuit_schedule")
public class CircuitSchedule extends AbstractEntity {

    private Date startDate;
    private Date endDate;
    private int frequencyDays;
    private int wateringTime;
    private boolean deactivated;

    @ManyToOne
    private Circuit circuit;

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
