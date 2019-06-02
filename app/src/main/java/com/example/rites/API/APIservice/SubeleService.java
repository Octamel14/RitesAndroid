package com.example.rites.API.APIservice;

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

public interface SubeleService {


    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRides2();

    @Headers( "Content-Type: application/json" )
    @GET("/users/?")
    Call<List<User>> getUserLogin(@Query("email") String email, @Query("password") String password);
    //@Headers( "Content-Type: application/json" )

    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter/?")
    Call<List<RideFilter>> getRidesByDate(@Query("hour") String hour);
    //@POST("/rides/")
    //Call <ResponseBody>  CreateRide(@Body PostRide ride);




}
