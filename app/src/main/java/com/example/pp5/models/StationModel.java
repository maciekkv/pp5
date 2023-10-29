package com.example.pp5.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    String geo;
    int id;
    String name;

    //constructor
    public StationModel(String address, String LPG, String PB95, String PB98, String ON, String icon, String geo, int id, String name) {
        this.address = address;
        this.LPG = LPG;
        this.PB95 = PB95;
        this.PB98 = PB98;
        this.ON = ON;
        this.icon = icon;
        this.isFavourite = false;
        this.geo = geo;
        this.id = id;
        this.name = name;
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

    public String getGeo() {return geo;}

    public int getId() {
        return id;
    }

    public String getName() { return name; }

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


    public static Comparator<StationModel> nameComparator(String selectedBrand) {
        return new Comparator<StationModel>() {
            @Override
            public int compare(StationModel o1, StationModel o2) {
                // Check, if stations starts with same brand
                boolean isBrand1 = o1.getName().startsWith(selectedBrand);
                boolean isBrand2 = o2.getName().startsWith(selectedBrand);

                // Set the position for station, first with selected brand,then with other
                if (isBrand1 && !isBrand2) {
                    return -1;
                } else if (!isBrand1 && isBrand2) {
                    return 1;
                } else {
                    // if both are the same or none of them, rest sort alphabetically
                    return o1.getName().compareTo(o2.getName());
                }
            }
        };
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    // Setter dla pola isFavourite
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }



    //coordinates
    public static class GeoCoordinates {
        double latitude;
        double longitude;

        //constructor
        public GeoCoordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
    GeoCoordinates geoCoordinates;


    //setter
    public void setGeoCoordinates(double latitude, double longitude) {
        this.geoCoordinates = new GeoCoordinates(latitude, longitude);
    }

    //getter
    public GeoCoordinates getGeoCoordinates() {
        return geoCoordinates;
    }
}
