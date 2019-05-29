package com.example.rites.API.APIservice;

import com.example.rites.models.Ride;
import com.example.rites.models.RideFilter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SubeleService {


    @Headers( "Content-Type: application/json" )
    @GET("/ridesfilter")
    Call<List<RideFilter>> getRides2();

    //@Headers( "Content-Type: application/json" )
    //@POST("/rides/")
    //Call <ResponseBody>  CreateRide(@Body PostRide ride);




}
