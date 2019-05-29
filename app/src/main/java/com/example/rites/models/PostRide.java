package com.example.rites.models;

public class PostRide {
    private Ride ride;

    public PostRide(Ride ride) {
        this.ride = ride;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
