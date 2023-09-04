package com.example.pp5.viewmodels;

import android.util.Log;

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

    private MutableLiveData<List<StationModel>> stationList;

    public StationListViewModel() {
        stationList = new MutableLiveData<>();
    }

    public MutableLiveData<List<StationModel>> getStationListObserver(){
        return stationList;
    }

    public void makeApiCall(){
        ApiServices apiServices = StationRepository.getRetrofitClient().create(ApiServices.class);
        Call<List<StationModel>> call = apiServices.getStationList();
        call.enqueue(new Callback<List<StationModel>>() {
            @Override
            public void onResponse(Call<List<StationModel>> call, Response<List<StationModel>> response) {
                stationList.postValue(response.body());
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
