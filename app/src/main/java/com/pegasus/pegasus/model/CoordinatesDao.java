package com.pegasus.pegasus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesDao implements Serializable {

    private String Latitude  = "";

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getIconColor() {
        return IconColor;
    }

    public void setIconColor(String iconColor) {
        IconColor = iconColor;
    }

    private String Longitude = "";
    private String Time = "";
    private String IconColor = "";



}
