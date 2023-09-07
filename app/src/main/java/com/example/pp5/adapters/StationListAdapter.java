package com.example.pp5.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pp5.R;
import com.example.pp5.models.StationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.myViewHolder> implements Filterable{

    List<StationModel> stationList;

    List<StationModel> stationListFull;

    //constructor
    public StationListAdapter(List<StationModel> stationList) {
        this.stationList = stationList;
        stationListFull = new ArrayList<>(stationList);
    }

    public void updateStationList(List<StationModel> list){
        this.stationList = list;
        stationListFull = new ArrayList<>(list);
        notifyDataSetChanged();
    }


    //adapter's methods
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create station in recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        //put fetched data into holders
        holder.txt1.setText(stationList.get(position).getAddress());
        holder.txt2.setText("LPG: "+stationList.get(position).getLPG());
        holder.txt3.setText("PB95: "+stationList.get(position).getPB95());
        holder.txt4.setText("PB98: "+stationList.get(position).getPB98());
        holder.txt5.setText("ON: "+stationList.get(position).getON());
        Glide.with(holder.itemView.getContext()).load(stationList.get(position).getIcon()).into(holder.img);
    }

    @Override
    public int getItemCount() {

        if(this.stationList!=null){
            return this.stationList.size();
        }else
            return 0;

    }

    @Override
    public Filter getFilter() {
        return stationFilter;
    }

    private Filter stationFilter = new Filter() {
        //background
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StationModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(stationListFull);
            } else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StationModel station : stationListFull){
                    if (station.getAddress().toLowerCase().contains(filterPattern))
                        filteredList.add(station);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        //UI
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stationList.clear();
            stationList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public class myViewHolder extends RecyclerView.ViewHolder{
        //reference to station_item layout
        ImageView img;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        public myViewHolder(@NonNull View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.ImageView);
            txt1 = itemView.findViewById(R.id.address);
            txt2 = itemView.findViewById(R.id.lpg);
            txt3 = itemView.findViewById(R.id.pb95);
            txt4 = itemView.findViewById(R.id.pb98);
            txt5 = itemView.findViewById(R.id.on);
        }
    }


}

