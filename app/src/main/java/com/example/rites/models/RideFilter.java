package com.example.rites.models;

public class RideFilter {
    private Host host;
    private String id_ride;
    private String starting_point;
    private String destination;
    private String date;
    private String hour;
    private String cost;
    //

    public RideFilter(Host host, String id_ride,String starting_point, String destination, String date, String hour, String cost) {
        this.host = host;
        this.id_ride = id_ride;
        this.starting_point = starting_point;
        this.destination = destination;
        this.date = date;
        this.hour = hour;
        this.cost = cost;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getId_ride() {return id_ride;}

    public void setId_ride(String id_ride) {this.id_ride = id_ride;}

    public String getStarting_point() {
        return starting_point;
    }

    public void setStarting_point(String starting_point) {
        this.starting_point = starting_point;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getCost() {return cost;}

    public void setcost(String cost) {this.cost = cost;}
}
