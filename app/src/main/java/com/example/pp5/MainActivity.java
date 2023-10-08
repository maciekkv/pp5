package com.example.pp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;



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



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_about:
                        Log.d("About", "About is clicked");
                        Toast.makeText(getApplicationContext(),"About app",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_stats:
                        Log.d("Stats", "Stats is clicked");
                        //Toast.makeText(MainActivity.this,"Statistic",Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FavouriteFragment()).commit();
                        break;
                    case R.id.nav_sync:
                        Log.d("Sync", "Sync is clicked");
                        Toast.makeText(getApplicationContext(),"Sync up to date",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_button:
                        Log.d("Button", "Button is clicked");
                        Toast.makeText(getApplicationContext(),"Enable button",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.main:
                        Log.d("Main", "Main is clicked");
                        Toast.makeText(getApplicationContext(),"Main Home",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });





        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
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