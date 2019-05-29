package com.example.rites.models;

import com.google.gson.annotations.Expose;

public class Rides {
    private int id_ride;
    private String starting_point;
    private String destination;
    private String date;
    private String hour;
    private int room;
    private int n_stops;
    private float cost;
    private int host;
    private int vehicle;

    Rides(){}



    public Rides(int id_ride, String starting_point, String date, String hour, int room, int n_stops, float cost, int host, int vehicle, String destination) {
        this.id_ride = id_ride;
        this.starting_point = starting_point;
        this.date = date;
        this.destination=destination;
        this.hour = hour;
        this.room = room;
        this.n_stops = n_stops;
        this.cost = cost;
        this.host = host;
        this.vehicle = vehicle;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getId_ride() {
        return id_ride;
    }

    public void setId_ride(int id_ride) {
        this.id_ride = id_ride;
    }

    public String getStarting_point() {
        return starting_point;
    }

    public void setStarting_point(String starting_point) {
        this.starting_point = starting_point;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getN_stops() {
        return n_stops;
    }

    public void setN_stops(int n_stops) {
        this.n_stops = n_stops;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }
}
