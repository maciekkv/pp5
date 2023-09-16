package com.example.pp5.repositories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //this work as bridge, connects api with app, make connection

    //declare variables
    //public static String baseurl = "https://petrol-stations.onrender.com/";
    public static String baseurl = "https://maciekkv.pythonanywhere.com/";
    //public static String baseurl = "https://8db4-46-204-109-132.ngrok-free.app/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
