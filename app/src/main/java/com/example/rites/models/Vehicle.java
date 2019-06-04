package com.example.rites.models;

public class Vehicle {

    private String id_vehicle;
    private String user_id;
    private String model;
    private String color;
    private String plates;

    public Vehicle(){}

    public Vehicle(String id_vehicle, String user_id, String model, String color, String plates){
        this.id_vehicle = id_vehicle;
        this.user_id = user_id;
        this.model = model;
        this.color = color;
        this.plates = plates;
    }

    public String getId_vehicle() {return id_vehicle;}

    public void setId_vehicle(String  id_vehicle){this.id_vehicle = id_vehicle;}

    public String getUser_id() {return user_id;}

    public void setUser_id(String  user_id){this.user_id = user_id;}

    public String getModel() {return model;}

    public void setModel(String  model){this.model = model;}

    public String getColor() {return color;}

    public void setColor(String  color){this.color = color;}

    public String getPlates() {return plates;}

    public void setPlates(String  plates){this.plates = plates;}
}
