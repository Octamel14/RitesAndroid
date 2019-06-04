package com.example.rites.models;

public class Vehicle {

    private String id_vehicle;
    private String user;
    private String model;
    private String color;
    private String plates;

    public Vehicle(){}

    public Vehicle(String id_vehicle, String user_id, String model, String color, String plates){
        this.id_vehicle = id_vehicle;
        this.user = user_id;
        this.model = model;
        this.color = color;
        this.plates = plates;
    }

    public String getId_vehicle() {return id_vehicle;}

    public void setId_vehicle(String  id_vehicle){this.id_vehicle = id_vehicle;}

    public String getUser() {return user;}

    public void setUser(String  user_id){this.user = user_id;}

    public String getModel() {return model;}

    public void setModel(String  model){this.model = model;}

    public String getColor() {return color;}

    public void setColor(String  color){this.color = color;}

    public String getPlates() {return plates;}

    public void setPlates(String  plates){this.plates = plates;}
}
