package com.example.pp5.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class StationModel   {
    //model class - select data which should be fetched from api

    //declare variables
    String address;
    String LPG;
    String PB95;
    String PB98;
    String ON;
    String icon;
    boolean isFavourite;

    //constructor
    public StationModel(String address, String LPG, String PB95, String PB98, String ON, String icon) {
        this.address = address;
        this.LPG = LPG;
        this.PB95 = PB95;
        this.PB98 = PB98;
        this.ON = ON;
        this.icon = icon;
        this.isFavourite = false;
    }





    public StationModel() {

    }

    //getters
    public String getAddress() {
        return address;
    }

    public String getLPG() {
        return LPG;
    }

    public String getPB95() {
        return PB95;
    }

    public String getPB98() {
        return PB98;
    }

    public String getON() {
        return ON;
    }

    public String getIcon() {
        return icon;
    }



    //sorting
    public static Comparator<StationModel> lpgComparator = new Comparator<StationModel>() {
        @Override
        public int compare(StationModel o1, StationModel o2) {
            return o1.getLPG().compareTo(o2.getLPG());
        }
    };

    public static Comparator<StationModel> pb95Comparator = new Comparator<StationModel>() {
        @Override
        public int compare(StationModel o1, StationModel o2) {
            return o1.getPB95().compareTo(o2.getPB95());
        }
    };

    public static Comparator<StationModel> pb98Comparator = new Comparator<StationModel>() {
        @Override
        public int compare(StationModel o1, StationModel o2) {
            return o1.getPB98().compareTo(o2.getPB98());
        }
    };

    public static Comparator<StationModel> onComparator = new Comparator<StationModel>() {
        @Override
        public int compare(StationModel o1, StationModel o2) {
            return o1.getON().compareTo(o2.getON());
        }
    };


    public boolean isFavourite() {
        return isFavourite;
    }

    // Setter dla pola isFavourite
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

}
