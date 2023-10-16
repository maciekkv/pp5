package com.example.pp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pp5.fragments.FavouriteFragment;
import com.example.pp5.fragments.HomeFragment;
import com.example.pp5.fragments.SearchFragment;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawerLayout);

        //to display system toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fab = findViewById(R.id.fab);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_about:
                        Log.d("About", "About is clicked");
                        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_stats:
                        Log.d("Stats", "Stats is clicked");
                        Toast.makeText(MainActivity.this,"Statistic",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_sync:
                        Log.d("Sync", "Sync is clicked");
                        Toast.makeText(getApplicationContext(), "Sync up to date", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_button_enable:
                        Log.d("Button", "Navigation button is enabled");
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_button_disable:
                        Log.d("Button", "Navigation button is disabled");
                        fab.setVisibility(View.GONE);
                        break;
                    case R.id.main:
                        Log.d("Main", "Main is clicked");
                        Intent intent2 = new Intent(MainActivity.this,CityActivity.class);
                        startActivity(intent2);
                        break;
                    default:
                        return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fab", "fab is clicked");
                Toast.makeText(getApplicationContext(), "Navigate to closest station", Toast.LENGTH_SHORT).show();
                //navigateToClosetStation();
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);

            }
        });


        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
    }


    private void navigateToClosetStation() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=50.28701587657711, 18.672802131331053&mode=1"));
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }






    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.home:
                    Log.d("HomeFragment", "Home Fragment is on");
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.search:
                    Log.d("SearchFragment", "Searcg Fragment is on");
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.favourite:
                    Log.d("FavouriteFragment", "Favourite Fragment is on");
                    selectedFragment = new FavouriteFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

            return true;
        }
    };


    //Enable navigation_drawer button on toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}