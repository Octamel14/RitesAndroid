package com.example.rites.models;

public class RideGuest {

    private String guest_id;
    private String ride;
    private String user;
    private int status;

    public RideGuest(){}

    public RideGuest(String guest_id,String ride_id, String user_id, int status){
        this.guest_id=guest_id;
        this.ride=ride_id;
        this.user=user_id;
        this.status = status;
    }

    public String getRide_id() {
        return ride;
    }

    public void setRide_id(String ride_id) {
        this.ride = ride_id;
    }

    public String getUser_id() {
        return user;
    }

    public void setUser_id(String user_id) {
        this.user = user_id;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public int getStatus(){return status;}

    public void setStatus(int status){this.status = status;}
}
