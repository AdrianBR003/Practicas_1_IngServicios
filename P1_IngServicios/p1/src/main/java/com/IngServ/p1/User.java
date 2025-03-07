package com.IngServ.p1;

import java.io.Serializable;

public class User implements Serializable {
    private String nameUser;
    private String name;
    private String lastname;
    private String email;


    public User() {
        this.nameUser = "";
        this.name = "";
        this.lastname = "";
        this.email = "";
    }

    public User(String nameUser, String name, String lastname, String email) {
        this.nameUser = nameUser;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "nameUser='" + nameUser + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
