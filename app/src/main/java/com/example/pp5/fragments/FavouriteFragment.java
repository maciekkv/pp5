package com.example.pp5.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pp5.MainActivity;
import com.example.pp5.R;
import com.example.pp5.adapters.FavouriteStationListAdapter;
import com.example.pp5.adapters.StationListAdapter;
import com.example.pp5.models.StationModel;
import com.example.pp5.viewmodels.StationListViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FavouriteFragment extends Fragment {

    RecyclerView recview;
    TextView noresult;
    ProgressBar progressBar;
    List<StationModel> stationList;
    StationListViewModel listViewModel;
    //StationListAdapter adapter;
    FavouriteStationListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<StationModel> favouriteStations;
    ItemTouchHelper itemTouchHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stationList = new ArrayList<>();
        adapter = new FavouriteStationListAdapter(requireContext(), stationList);
        listViewModel = new ViewModelProvider(this).get(StationListViewModel.class);
        favouriteStations = adapter.getFavouriteStations();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);

        recview = view.findViewById(R.id.recview);
        noresult = view.findViewById(R.id.noresult);

        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recview);




        if(favouriteStations.isEmpty()){
            noresult.setVisibility(View.GONE);
            recview.setVisibility(View.GONE);
        }else {
            noresult.setVisibility(View.GONE);
            recview.setVisibility(View.VISIBLE);
        }

        adapter.updateStationList(favouriteStations);
        recview.setAdapter(adapter);


        return view;


    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        //To remove station if user swipe
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            favouriteStations.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
            saveFavouriteStationsToSharedPreferences();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), com.google.android.material.R.color.design_default_color_background))
                    .addBackgroundColor(ContextCompat.getColor(getContext(),R.color.red))
                    .addSwipeLeftLabel("Delete")
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .setSwipeLeftLabelColor(R.color.white)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };
    private void saveFavouriteStationsToSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        //save updated favouriteStations list to sharedPref
        String json = gson.toJson(favouriteStations);
        editor.putString("station", json);
        editor.apply();
    }


}