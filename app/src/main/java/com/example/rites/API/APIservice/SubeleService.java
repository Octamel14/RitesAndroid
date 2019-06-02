package com.example.rites.API.APIservice;

import android.content.Intent;
import android.os.Bundle;

import com.example.rites.models.Ride;
import com.example.rites.models.RideFilter;
import com.example.rites.models.User;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
<<<<<<< HEAD

import android.content.Intent;
=======
>>>>>>> 2fb3644b533d30e651397b5f8cef51eaf84b3a29

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
    //@POST("/rides/")
    //Call <ResponseBody>  CreateRide(@Body PostRide ride);




}
