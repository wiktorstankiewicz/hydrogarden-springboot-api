package com.hydrogarden.server.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    private String userName;
    private String hashPassword;


}
