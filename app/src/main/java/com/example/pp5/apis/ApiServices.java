package com.example.pp5.apis;

import com.example.pp5.models.StationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    //make a query, gets objects from endpoint and store them in list

    @GET("Gliwice")         //endpoint
    Call<List<StationModel>> getStationList();
}
