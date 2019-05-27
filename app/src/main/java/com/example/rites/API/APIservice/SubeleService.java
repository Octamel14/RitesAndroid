package com.example.rites.API.APIservice;

import com.example.rites.models.Rides;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface SubeleService {

    /*
        @GET("/rides")
        Call<List<Rides>> getRidesByDate(@Query("date")  String date);

    */
    @Headers( "Content-Type: application/json" )
    @GET("/rides")
   // void getRides();
    Call<List<Rides>> getRides2();
}
