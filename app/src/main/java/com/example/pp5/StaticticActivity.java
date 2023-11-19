package com.example.pp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pp5.models.StationModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
        TextView textView;
        textView = findViewById(R.id.txtViewNoData);

        BarChart barChart = findViewById(R.id.barChart);

        List<StationModel> favouriteStations = getFavouriteStations();

        if (favouriteStations.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        }

        ArrayList<BarEntry> entriesPB95 = new ArrayList<>();
        ArrayList<BarEntry> entriesPB98 = new ArrayList<>();
        ArrayList<BarEntry> entriesON = new ArrayList<>();
        ArrayList<BarEntry> entriesLPG = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for(int i=0; i<favouriteStations.size(); i++){
            StationModel station = favouriteStations.get(i);

            Log.d("StationData", "Station: " + station.getName()+ " " +  station.getAddress() + " " + station.getPB95());

            //float value = Float.parseFloat(station.getPB95());
            entriesPB95.add(new BarEntry(i, Float.parseFloat(station.getPB95())));
            entriesPB98.add(new BarEntry(i, Float.parseFloat(station.getPB98())));
            entriesON.add(new BarEntry(i, Float.parseFloat(station.getON())));
            entriesLPG.add(new BarEntry(i, Float.parseFloat(station.getLPG())));
            labels.add(station.getName()+" - "+ station.getAddress());
        }


        BarDataSet barDataSetPB95 = new BarDataSet(entriesPB95, "PB95");
        barDataSetPB95.setColors(ColorTemplate.MATERIAL_COLORS[0]);
        barDataSetPB95.setValueTextColor(Color.BLACK);
        barDataSetPB95.setValueTextSize(15f);
        ///
        BarDataSet barDataSetPB98 = new BarDataSet(entriesPB98, "PB98");
        barDataSetPB98.setColors(ColorTemplate.MATERIAL_COLORS[1]);
        barDataSetPB98.setValueTextColor(Color.BLACK);
        barDataSetPB98.setValueTextSize(15f);
        ///
        BarDataSet barDataSetON = new BarDataSet(entriesON, "ON");
        barDataSetON.setColors(ColorTemplate.MATERIAL_COLORS[2]);
        barDataSetON.setValueTextColor(Color.BLACK);
        barDataSetON.setValueTextSize(15f);
        ///
        BarDataSet barDataSetLPG = new BarDataSet(entriesLPG, "LPG");
        barDataSetLPG.setColors(ColorTemplate.MATERIAL_COLORS[3]);
        barDataSetLPG.setValueTextColor(Color.BLACK);
        barDataSetLPG.setValueTextSize(15f);






        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        //barChart.getDescription().setText("Fuel prices visualization");
        barChart.getDescription().setEnabled(false);

        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setGranularityEnabled(true);


        float barWidth =0.12f;
        float barSpace =0.05f;
        float groupSpace=0.32f;

        BarData barData = new BarData(barDataSetPB95,barDataSetPB98,barDataSetON,barDataSetLPG);
        barData.setBarWidth(barWidth);
        barChart.setData(barData);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(2);

        barChart.moveViewToX(0);
        barChart.setFitBars(true);

        float groupWidth = barData.getGroupWidth(groupSpace, barSpace);

        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + groupWidth * favouriteStations.size());
        barChart.groupBars(0,groupSpace,barSpace);
        barChart.getAxisLeft().setAxisMinimum(0);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setLabelCount(10);
        //yAxis.setAxisLineColor(Color.BLACK);
        //yAxis.setAxisLineWidth(2f);

        barChart.animateY(1500);
        barChart.invalidate();



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