package com.hydrogarden.server;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User extends AbstractEntity{

    private String userName;
    private String hashPassword;

}
