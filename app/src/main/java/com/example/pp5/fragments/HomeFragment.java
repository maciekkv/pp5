package com.example.pp5.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pp5.R;
import com.example.pp5.adapters.StationListAdapter;
import com.example.pp5.models.StationModel;
import com.example.pp5.viewmodels.StationListViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recview;
    TextView noresult;
    ProgressBar progressBar;
    List<StationModel> stationList;

    StationListViewModel listViewModel;
    StationListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stationList = new ArrayList<>();

        adapter = new StationListAdapter(stationList);
        //recview.setAdapter(adapter);


        listViewModel = new ViewModelProvider(this).get(StationListViewModel.class);
        listViewModel.getStationListObserver().observe(this, new Observer<List<StationModel>>() {
            @Override
            public void onChanged(List<StationModel> stationModels) {
                if(stationModels!=null){
                    stationList = stationModels;
                    adapter.updateStationList(stationModels);
                    noresult.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
                if(stationModels==null){
                    recview.setVisibility(View.GONE);
                    noresult.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        listViewModel.makeApiCall();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        recview = view.findViewById(R.id.recview);
        noresult = view.findViewById(R.id.noresult);
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        recview.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeUpdate();
            }
        });

        return view;
    }

    private void swipeUpdate(){
        listViewModel.makeApiCall();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(),"All prices are up to date",Toast.LENGTH_SHORT).show();
    }
}