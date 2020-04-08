package com.pegasus.pegasus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackingDetailsDao implements Serializable {

    private int error_number = 0;
    private String error_description = "";
    private String currentLocationLatitude = "";
    private String currentLocationLongitude = "";
    private String originLocationColor ="";
    private String destinationLocationColor = "";
    private String currentLocationColor = "";
    private int count = 0;
    private boolean status = false;


    private List<CoordinatesDao> coordinatesDaoList = new ArrayList<>();

    public int getError_number() {
        return error_number;
    }

    public void setError_number(int error_number) {
        this.error_number = error_number;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getCurrentLocationLatitude() {
        return currentLocationLatitude;
    }

    public void setCurrentLocationLatitude(String currentLocationLatitude) {
        this.currentLocationLatitude = currentLocationLatitude;
    }

    public String getCurrentLocationLongitude() {
        return currentLocationLongitude;
    }

    public void setCurrentLocationLongitude(String currentLocationLongitude) {
        this.currentLocationLongitude = currentLocationLongitude;
    }

    public String getOriginLocationColor() {
        return originLocationColor;
    }

    public void setOriginLocationColor(String originLocationColor) {
        this.originLocationColor = originLocationColor;
    }

    public String getDestinationLocationColor() {
        return destinationLocationColor;
    }

    public void setDestinationLocationColor(String destinationLocationColor) {
        this.destinationLocationColor = destinationLocationColor;
    }

    public String getCurrentLocationColor() {
        return currentLocationColor;
    }

    public void setCurrentLocationColor(String currentLocationColor) {
        this.currentLocationColor = currentLocationColor;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CoordinatesDao> getCoordinatesDaoList() {
        return coordinatesDaoList;
    }

    public void setCoordinatesDaoList(List<CoordinatesDao> coordinatesDaoList) {
        this.coordinatesDaoList = coordinatesDaoList;
    }

}
