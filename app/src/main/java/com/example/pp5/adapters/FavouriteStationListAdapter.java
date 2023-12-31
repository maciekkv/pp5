package com.example.pp5.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pp5.CityActivity;
import com.example.pp5.MainActivity;
import com.example.pp5.R;
import com.example.pp5.fragments.FavouriteFragment;
import com.example.pp5.models.StationModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavouriteStationListAdapter  extends RecyclerView.Adapter<FavouriteStationListAdapter.myViewHolder> {

    List<StationModel> stationList;

    List<StationModel> stationListFull;
    List<StationModel> favouriteStations;
    Context context;
    //constructor
    public FavouriteStationListAdapter(Context context,List<StationModel> stationList) {
        this.context = context;
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
    public FavouriteStationListAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create station in recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_station_item,parent,false);
        return new FavouriteStationListAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteStationListAdapter.myViewHolder holder, int position) {
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
    public class myViewHolder extends RecyclerView.ViewHolder {
        //reference to station_item layout
        ImageView img;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        Button favBtn;
        Button navBtn;


        public myViewHolder(@NonNull View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.ImageView);
            txt1 = itemView.findViewById(R.id.address);
            txt2 = itemView.findViewById(R.id.lpg);
            txt3 = itemView.findViewById(R.id.pb95);
            txt4 = itemView.findViewById(R.id.pb98);
            txt5 = itemView.findViewById(R.id.on);
            favBtn = itemView.findViewById(R.id.favBtn);
            navBtn = itemView.findViewById(R.id.nav_btn);
            navBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Favourite navigation button clicked",Toast.LENGTH_SHORT).show();

                    StationModel.GeoCoordinates coordinates = stationList.get(getAdapterPosition()).getGeoCoordinates();
                    if(coordinates != null){
                        String geoUri = "google.navigation:q=" + coordinates.getLatitude() + "," + coordinates.getLongitude() + "&mode=1";

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                        intent.setPackage("com.google.android.apps.maps");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context,"Coordinates not found",Toast.LENGTH_SHORT).show();
                    }

                }
            });


            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    StationModel station = stationList.get(position);

                    //remove from favourites
                    removeFromFavourites(station);
                    stationList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show();
                    saveToFavourites();
                    //notifyDataSetChanged();

                    if(stationList.isEmpty()){
                        notifyDataSetChanged();
                        FavouriteFragment.noresult.setVisibility(View.VISIBLE);
                        FavouriteFragment.noFav1.setVisibility(View.VISIBLE);
                        FavouriteFragment.noFav2.setVisibility(View.VISIBLE);

                    }

                }
            });

        }


    }

    private void saveToFavourites(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<StationModel> favouriteStations = getFavouriteStations();
        favouriteStations.clear();
        favouriteStations.addAll(stationList);

        String json = gson.toJson(favouriteStations);
        editor.putString("station",json);
        editor.apply();
    }


    //method to remove from favourites
    private void removeFromFavourites(StationModel station){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<StationModel> favouriteStations = getFavouriteStations();
        if (favouriteStations.contains(station)) {
            favouriteStations.remove(station);

            notifyDataSetChanged();
            updateStationList(favouriteStations);

            String json = gson.toJson(favouriteStations);
            editor.putString("station", json);
            editor.apply();
        }

    }



    public List<StationModel> getFavouriteStations(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("station",null);
        Type type = new TypeToken<ArrayList<StationModel>>() {}.getType();
        List<StationModel> favouriteStations = gson.fromJson(json,type);

        if(favouriteStations == null){
            favouriteStations = new ArrayList<>();
        }
        return favouriteStations;
    }



}
