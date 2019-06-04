package com.example.rites.models;

public class Ride {
    private String id_ride;
    private String starting_point;
    private String destination;
    private String date;



    private String hour;
    private String room;
    private String n_stops;
    private String cost;
    private String host;
    private String vehicle;
    private String is_active;

    Ride(){}



    public Ride(String id_ride, String starting_point, String date, String hour, String room, String n_stops, String cost, String host, String vehicle, String destination, String is_active) {
        this.id_ride = id_ride;
        this.starting_point = starting_point;
        this.date = date;
        this.destination=destination;
        this.hour = hour;
        this.room = room;
        this.n_stops = n_stops;
        this.cost = cost;
        this.is_active=is_active;
        this.host = host;
        this.vehicle = vehicle;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getId_ride() {
        return id_ride;
    }

    public void setId_ride(String id_ride) {
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getN_stops() {
        return n_stops;
    }

    public void setN_stops(String n_stops) {
        this.n_stops = n_stops;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
