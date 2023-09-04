package com.example.pp5.repositories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StationRepository {

    //this work as bridge, connects api with app, make connection

    //declare variables
    //public static String baseurl = "https://petrol-stations.onrender.com/";
    public static String baseurl = "https://maciekkv.pythonanywhere.com/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
