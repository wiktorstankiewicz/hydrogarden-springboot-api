package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "circuit")
public class Circuit extends AbstractEntity {

    private String code;

    private String name;

    @OneToMany(mappedBy = "circuit")
    private List<CircuitSchedule> circuitSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public List<CircuitSchedule> getCircuitSchedule() {
        return circuitSchedule;
    }

    public void setCircuitSchedule(List<CircuitSchedule> circuitSchedule) {
        this.circuitSchedule = circuitSchedule;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
