package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.*;

@Entity
@Table(name = "circuit_schedule")
public class CircuitSchedule extends AbstractEntity {

    private LocalTime startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private int frequencyDays;
    private int wateringTime;
    private boolean deactivated;

    @ManyToOne
    private Circuit circuit;

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
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
