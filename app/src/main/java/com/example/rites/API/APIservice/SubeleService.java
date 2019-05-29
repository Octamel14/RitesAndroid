package com.example.rites.API.APIservice;

import com.example.rites.models.Ride;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SubeleService {

    /*
        @GET("/rides")
        Call<List<Ride>> getRidesByDate(@Query("date")  String date);

    */

    @Headers( "Content-Type: application/json" )
    @GET("/rides")
    Call<List<Ride>> getRides2();

    @POST("/rides")
    void CreateRide(@Body Ride ride);
}
