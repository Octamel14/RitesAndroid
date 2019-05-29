package com.example.rites.models;

public class RideFilter {
    private Host host;
    private String starting_point;
    private String destination;
    private String date;
    private String hour;

    public RideFilter(Host host, String starting_point, String destination, String date, String hour) {
        this.host = host;
        this.starting_point = starting_point;
        this.destination = destination;
        this.date = date;
        this.hour = hour;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

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
}
