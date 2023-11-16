package com.example.pp5.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pp5.R;
import com.example.pp5.adapters.StationListAdapter;
import com.example.pp5.models.StationModel;
import com.example.pp5.viewmodels.StationListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recview;
    TextView noresult, txtViewNoConnection,txtViewNoConnection2,txtViewNoConnection3;
    ProgressBar progressBar;
    List<StationModel> stationList;

    StationListViewModel listViewModel;
    StationListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        stationList = new ArrayList<>();

        adapter = new StationListAdapter(requireContext(),stationList);



        listViewModel = new ViewModelProvider(this).get(StationListViewModel.class);
        listViewModel.getStationListObserver().observe(this, new Observer<List<StationModel>>() {
            @Override
            public void onChanged(List<StationModel> stationModels) {
                if(stationModels!=null){
                    Log.d("HomeFragment", "Station list size: " + stationModels.size());
                    stationList = stationModels;
                    adapter.updateStationList(stationModels);
                    adapter.notifyDataSetChanged();
                    noresult.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    txtViewNoConnection.setVisibility(View.GONE);
                    txtViewNoConnection2.setVisibility(View.GONE);
                    txtViewNoConnection3.setVisibility(View.GONE);
                }
                if(stationModels==null){
                    Log.d("HomeFragment", "No connection");
                    recview.setVisibility(View.GONE);
                    noresult.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtViewNoConnection.setVisibility(View.VISIBLE);
                            txtViewNoConnection2.setVisibility(View.VISIBLE);
                            txtViewNoConnection3.setVisibility(View.VISIBLE);
                        }
                    },2000);
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
        txtViewNoConnection = view.findViewById(R.id.txtViewNoConnection);
        txtViewNoConnection2 = view.findViewById(R.id.txtViewNoConnection2);
        txtViewNoConnection3 = view.findViewById(R.id.txtViewNoConnection3);

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

    //Extend it, different toast if values have not been changed
    private void swipeUpdate(){
        listViewModel.makeApiCall();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(),"All prices are up to date",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }




    //sorting
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.lpg_sort:
                Log.d("HomeFragment", "LPG Sort selected");
                Toast.makeText(getContext(),"Sorted by LPG",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.lpgComparator);
                adapter.notifyDataSetChanged();

                break;
            case R.id.pb95_sort:
                Log.d("Pb95 Sort", "Pb95 Sort selected");
                Toast.makeText(getContext(),"Sorted by PB95",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.pb95Comparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.pb98_sort:
                Log.d("Pb98 Sort", "Pb98 Sort selected");
                Toast.makeText(getContext(),"Sorted by PB98",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.pb98Comparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.on_sort:
                Log.d("OnSort", "On Sort selected");
                Toast.makeText(getContext(),"Sorted by ON",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.onComparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.orlen_sort:
                Log.d("OrlenSort", "Orlen Sort selected");
                Toast.makeText(getContext(),"Sorted by Orlen",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.nameComparator("ORLEN"));
                adapter.notifyDataSetChanged();
                break;
            case R.id.bp_sort:
                Log.d("BPSort", "BP Sort selected");
                Toast.makeText(getContext(),"Sorted by BP",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.nameComparator("BP"));

                adapter.notifyDataSetChanged();
                break;
            case R.id.shell_sort:
                Log.d("ShellSort", "Shell Sort selected");
                Toast.makeText(getContext(),"Sorted by Shell",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.nameComparator("SHELL"));
                adapter.notifyDataSetChanged();
                break;
            case R.id.amic_sort:
                Log.d("AmicSort", "Amic Sort selected");
                Toast.makeText(getContext(),"Sorted by Amic",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.nameComparator("AMIC"));
                adapter.notifyDataSetChanged();
                break;
            case R.id.circle_sort:
                Log.d("CircleSort", "Circle Sort selected");
                Toast.makeText(getContext(),"Sorted by Circle",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.nameComparator("CIRCLE K EXPRESS"));
                adapter.notifyDataSetChanged();
                break;
            case R.id.moya_sort:
                Log.d("MoyaSort", "Moya Sort selected");
                Toast.makeText(getContext(),"Sorted by Moya",Toast.LENGTH_SHORT).show();
                Collections.sort(stationList, StationModel.nameComparator("MOYA"));
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }




}