package com.example.rites.models;

import java.io.Serializable;

public class Host implements Serializable {
    private int id_user;
    private String first_name;
    private String last_name;

    public Host(){}

    public Host(int id_user, String first_name, String last_name) {
        this.id_user = id_user;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
