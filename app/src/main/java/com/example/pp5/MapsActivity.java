package com.example.pp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.pp5.models.StationModel;
import com.example.pp5.viewmodels.StationListViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap myMap;
    FrameLayout map;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 100;
    StationListViewModel stationListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map = findViewById(R.id.map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        stationListViewModel = new ViewModelProvider(this).get(StationListViewModel.class);

        //to check current location
        getLastLocation();


        //observe station list
        observeStationList();

        stationListViewModel.makeApiCall();
    }

    private void observeStationList() {
        stationListViewModel.getStationListObserver().observe(this, new Observer<List<StationModel>>() {
            @Override
            public void onChanged(List<StationModel> stationList) {
                if (stationList != null && !stationList.isEmpty()) {
                    LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(myLocation).title("My location"));
                    myMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    myMap.animateCamera(CameraUpdateFactory.zoomTo(18));

                    findAndNavigateToClosestStation(myLocation, stationList);
                }
            }
        });
    }



    private void getLastLocation() {
        //check if there are permission to check location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);


                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng myLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(myLocation).title("My location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        myMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        Log.d("Current location: ",myLocation.toString());
        Toast.makeText(this,"Current location: "+ myLocation.toString(),Toast.LENGTH_LONG).show();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"Location Permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //find and navigate to closest station
    private void findAndNavigateToClosestStation(LatLng myLocation, List<StationModel> stationList) {
        if (currentLocation != null) {
            //object of current location
            Location myLocationObject = new Location("MyLocation");
            myLocationObject.setLatitude(myLocation.latitude);
            myLocationObject.setLongitude(myLocation.longitude);

            //find closest station from list of available stations
            StationModel closestStation = findClosestStation(myLocationObject, stationList);
            if (closestStation != null) {
                StationModel.GeoCoordinates stationCoordinates = closestStation.getGeoCoordinates();
                if (stationCoordinates != null) {
                    LatLng stationLocation = new LatLng(stationCoordinates.getLatitude(), stationCoordinates.getLongitude());
                    navigateToStation(stationLocation);
                } else {
                    Toast.makeText(this, "Selected station has no coordinates", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //find and calculate distance to closet station
    private StationModel findClosestStation(Location myLocation, List<StationModel> stationList) {
        StationModel closestStation = null;
        //min distance to station
        float minDistance = Float.MAX_VALUE;

        //check if station has coordinates and if yes create object of Location
        for (StationModel station : stationList) {
            StationModel.GeoCoordinates stationCoordinates = station.getGeoCoordinates();
            if (stationCoordinates != null) {
                Location stationLocation = new Location("StationLocation");
                stationLocation.setLatitude(stationCoordinates.getLatitude());
                stationLocation.setLongitude(stationCoordinates.getLongitude());

                //calculate distance from mylocation to closet station
                float distance = myLocation.distanceTo(stationLocation);
                //compare distance
                if (distance < minDistance) {
                    minDistance = distance;
                    closestStation = station;
                    Log.d("Distance", String.valueOf(distance));
                    Log.d("Closest station", String.valueOf(closestStation));
                }
            }
        }

        return closestStation;
    }

    //navigate to closest station
    private void navigateToStation(LatLng stationLocation) {
        Log.d("MapsActivity", "Navigating to station: " + stationLocation.toString());
        Uri uri = Uri.parse("google.navigation:q=" + stationLocation.latitude + "," + stationLocation.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
        }
    }

}