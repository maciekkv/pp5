package com.example.pp5.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pp5.apis.ApiServices;
import com.example.pp5.models.StationModel;
import com.example.pp5.repositories.StationRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationListViewModel extends ViewModel {

    private static MutableLiveData<List<StationModel>> stationList;
    private String currentCity ;

    private void updateBaseUrl(){
        StationRepository.updateBaseUrl();
    }

    public StationListViewModel() {
        stationList = new MutableLiveData<>();
        currentCity = StationRepository.getCurrentCity();
    }

    public MutableLiveData<List<StationModel>> getStationListObserver(){
        return stationList;
    }

    public void setCurrentCity(String city){
        this.currentCity = city;
    }



    //make the connection
    public void makeApiCall(){
        updateBaseUrl();
        StationRepository.setCurrentCity(currentCity);
        ApiServices apiServices = StationRepository.getRetrofitClient().create(ApiServices.class);
        Call<List<StationModel>> call = apiServices.getStationList(currentCity);
        call.enqueue(new Callback<List<StationModel>>() {
            @Override
            public void onResponse(Call<List<StationModel>> call, Response<List<StationModel>> response) {
                //stationList.postValue(response.body());
                List<StationModel> stations = response.body();
                if(stations != null){
                    //update coordinates for each station
                    for (StationModel station: stations){
                        String[] geo = station.getGeo().split(", ");
                        double latitude = Double.parseDouble(geo[0]);
                        double longitude = Double.parseDouble(geo[1]);
                        //set coordinates x,y
                        station.setGeoCoordinates(latitude,longitude);
                    }
                    stationList.postValue(stations);
                }
            }


            //To do when connection wont work
            @Override
            public void onFailure(Call<List<StationModel>> call, Throwable t) {
                stationList.postValue(null);
                Log.e("Error: ",t.getMessage().toString());
            }
        });
    }



}
