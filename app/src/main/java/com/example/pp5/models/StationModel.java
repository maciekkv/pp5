package com.example.pp5.models;

public class StationModel {

    String address;
    String LPG;
    String PB95;
    String PB98;
    String ON;
    String icon;

    public StationModel(String address, String LPG, String PB95, String PB98, String ON, String icon) {
        this.address = address;
        this.LPG = LPG;
        this.PB95 = PB95;
        this.PB98 = PB98;
        this.ON = ON;
        this.icon = icon;
    }

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
}
