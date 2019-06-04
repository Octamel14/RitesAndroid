package com.example.rites.API.APIservice;

import android.content.Intent;
import android.os.Bundle;

import com.example.rites.models.IntermediateStop;
import com.example.rites.models.Ride;
import com.example.rites.models.RideFilter;
import com.example.rites.models.User;
import com.example.rites.models.Vehicle;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import android.content.Intent;

public interface SubeleService {
    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRidesOrigin(@Query("starting_point") String starting_point);

    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRidesDestination(@Query("destination") String destination);

    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRidesHour(@Query("hour") String hour);

    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRidesStop(@Query("stop") String stop);

    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRides();

    @Headers( "Content-Type: application/json" )
    @GET("/users/?")
    Call<List<User>> getUserLogin(@Query("email") String email, @Query("password") String password);
    //@Headers( "Content-Type: application/json" )

    @Headers( "Content-Type: application/json" )
    @GET("/rides/?")
    Call<List<Ride>> getRideDetails(@Query("id_ride") String id_ride);

    @Headers( "Content-Type: application/json" )
    @GET("/intermediatestops/?")
    Call<List<IntermediateStop>> getStop(@Query("ride_id") String ride_id);

    @Headers( "Content-Type: application/json" )
    @GET("/vehicles/?")
    Call<List<Vehicle>> getVehicle(@Query("id_vehicle") String id_vehicle);

    //@POST("/rides/")
    //Call <ResponseBody>  CreateRide(@Body PostRide ride);
    @Headers("Content-Type: application/json" )
    @POST("/users/")
    Call<User> postUser(@Body User user);

    @Headers("Content-Type: application/json" )
    @POST("/vehicles/")
    Call<Vehicle> postVehicle(@Body Vehicle vehicle);



}
