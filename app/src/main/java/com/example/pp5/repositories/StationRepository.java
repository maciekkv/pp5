package com.example.pp5.repositories;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StationRepository {

    //this work as bridge, connects api with app

    //declare variables
    //public static String baseurl = "https://petrol-stations.onrender.com/";
    public static String baseurl = "https://maciekkv.pythonanywhere.com/";
    //public static String baseurl = "https://4716-46-204-105-97.ngrok-free.app/";

    private static Retrofit retrofit;

    private static String currentCity = "Gliwice";

    public static String getCurrentCity(){
        return currentCity;
    }

    public static void setCurrentCity(String city){
        currentCity = city;
    }

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static void updateBaseUrl() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("StationRepository", "Base URL updated to: " + baseurl+currentCity);

    }
}
