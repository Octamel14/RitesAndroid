package com.example.rites.models;

public class IntermediateStop {

    private String id_stop;
    private String ride_id;
    private String place;

    public IntermediateStop(){}

    public IntermediateStop(String id_stop, String ride_id, String place){
        this.id_stop = id_stop;
        this.ride_id = ride_id;
        this.place = place;
    }

    public String getId_stop() {return id_stop;}

    public void setId_stop(String id_stop) { this.id_stop = id_stop; }

    public String getRide_id() {return ride_id;}

    public void setRide_id(String ride_id) { this.ride_id  = ride_id; }

    public String getPlace() {return place;}

    public void setPlace(String place) { this.place = place; }
}
