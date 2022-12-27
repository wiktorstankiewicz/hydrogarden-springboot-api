package com.hydrogarden.server.circuit;

import com.hydrogarden.server.AbstractEntity;
import com.hydrogarden.server.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="circuit")
public class Circuit extends AbstractEntity {

    private String code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

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
