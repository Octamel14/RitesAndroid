package com.example.rites.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class LogedUser extends RealmObject {

    @PrimaryKey
    private Integer id_user;

    private String first_name;

    private String last_name;

    private String email;
    private String password;

    private Boolean is_rider;

    private int rider_score;
    private int riders_number;
    private int scored;

    public LogedUser(){}

    public LogedUser(User user)
    {
        id_user = user.getId_user();
        first_name = user.getFirst_name();
        last_name = user.getLast_name();
        email = user.getEmail();
        password = user.getPassword();
        is_rider = user.getIs_rider();
        rider_score = user.getRider_score();
        riders_number = user.getRiders_number();
        scored = user.getScored();
    }

    public LogedUser(Integer id_user, String first_name, String last_name, String email, String password, Boolean is_rider, int rider_score, int riders_number, int scored) {
        this.id_user = id_user;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.is_rider = is_rider;
        this.rider_score = rider_score;
        this.riders_number = riders_number;
        this.scored = scored;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_rider() {
        return is_rider;
    }

    public void setIs_rider(Boolean is_rider) {
        this.is_rider = is_rider;
    }

    public int getRider_score() {
        return rider_score;
    }

    public void setRider_score(int rider_score) {
        this.rider_score = rider_score;
    }

    public int getRiders_number() {
        return riders_number;
    }

    public void setRiders_number(int riders_number) {
        this.riders_number = riders_number;
    }

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }
}
