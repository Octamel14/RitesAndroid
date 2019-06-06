package com.example.rites.models;

public class RideGuestFiler {

    private String guest_id;
    private RideFilter ride; //no se mueve, es object
    private String status;
    private String evaluated;

    public RideGuestFiler(String guest_id, RideFilter ride, String status, String evaluated){
        this.guest_id=guest_id;
        this.ride=ride;
        this.status=status;
        this.evaluated=evaluated;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public RideFilter getRideFilter() {
        return ride;
    }

    public void setRideFilter(RideFilter rideFilter) {
        this.ride = rideFilter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvalueted() {
        return evaluated;
    }

    public void setEvalueted(String evalueted) {
        this.evaluated = evalueted;
    }
}
