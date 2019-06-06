package com.example.rites.models;

public class Solicitud {

    private String guest_id;
    private String ride;
    private Host user;
    private int status;

    public Solicitud(String guest_id, String ride, Host user, int status)
    {
        this.guest_id = guest_id;
        this.ride = ride;
        this.user = user;
        this.status = status;
    }

    public String getGuest_id(){
        return guest_id;
    }
    public void setGuest_id(String guest_id){
        this.guest_id = guest_id;
    }
    public String getRide(){
        return ride;
    }
    public void setRide(String ride){
        this.ride = ride;
    }
    public Host getUser(){
        return user;
    }
    public void setUser(Host user){
        this.user = user;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status = status;
    }
}
