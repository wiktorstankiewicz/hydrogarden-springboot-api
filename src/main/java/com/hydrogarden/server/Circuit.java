package com.hydrogarden.server;

import jakarta.persistence.*;

@Entity
@Table(name="circuit")
public class Circuit extends AbstractEntity{

    private String code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
