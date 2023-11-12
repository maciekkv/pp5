package com.example.pp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.pp5.models.StationModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StaticticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statictic);

        BarChart barChart = findViewById(R.id.barChart);

        List<StationModel> favouriteStations = getFavouriteStations();

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for(int i=0; i<favouriteStations.size(); i++){
            StationModel station = favouriteStations.get(i);

            Log.d("StationData", "Station: " + station.getName()+ " " +  station.getAddress() + " " + station.getPB95());

            float value = Float.parseFloat(station.getPB95());
            entries.add(new BarEntry(i,value));
            labels.add(station.getName()+"\n"+ station.getAddress());
        }


        BarDataSet barDataSet = new BarDataSet(entries,"PB95 Prices");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);


        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        //barChart.getDescription().setText("PB95 prices for favourite stations");
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.animateY(2000);
    }
    private List<StationModel> getFavouriteStations() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("station", null);

        Type type = new TypeToken<ArrayList<StationModel>>() {}.getType();
        List<StationModel> favouriteStations = gson.fromJson(json, type);

        if (favouriteStations == null) {
            favouriteStations = new ArrayList<>();
        }
        return favouriteStations;

    }




}