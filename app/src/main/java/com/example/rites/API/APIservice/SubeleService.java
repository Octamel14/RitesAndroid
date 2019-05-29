package com.example.rites.API.APIservice;

import com.example.rites.models.PostRide;
import com.example.rites.models.Ride;

import java.util.List;

import okhttp3.ResponseBody;
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

    @Headers( "Content-Type: application/json" )
    @POST("/rides/")
    Call <ResponseBody>  CreateRide(@Body PostRide ride);
   // @FormUrlEncoded
   // @Headers( "Content-Type: application/json" )
    //@POST("/rides/")
    //Call<ResponseBody> CreateRide2(@Field("id_ride") Integer id_ride, @Field("starting_point") String starting_point, @Field("destination") String destination, @Field("date") String date, @Field("hour") String hour, @Field("room") Integer room,
                                 //@Field("n_stops") Integer n_stops, @Field("cost") Float cost, @Field("host") Integer host, @Field("vehicle") Integer vehicle);



}
