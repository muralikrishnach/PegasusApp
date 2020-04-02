package com.pegasus.pegasus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDetailsDao implements Serializable {

    private int error_number = 0;
    private String error_description = "";
    private int openshipment_count = 0;
    private int podshipment_count = 0;
    private boolean status = false;

    private List<OpenShipmentsDao> openShipmentsDaoList = new ArrayList<>();

    private List<PODShipmentsDao> podShipmentsDaoList = new ArrayList<>();

    private List<ShipmentDetailsDao> shipmentDetailsDaoList = new ArrayList<>();



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

    public int getOpenshipment_count() {
        return openshipment_count;
    }

    public void setOpenshipment_count(int openshipment_count) {
        this.openshipment_count = openshipment_count;
    }

    public int getPodshipment_count() {
        return podshipment_count;
    }

    public void setPodshipment_count(int podshipment_count) {
        this.podshipment_count = podshipment_count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<OpenShipmentsDao> getOpenShipmentsDaoList() {
        return openShipmentsDaoList;
    }

    public void setOpenShipmentsDaoList(List<OpenShipmentsDao> openShipmentsDaoList) {
        this.openShipmentsDaoList = openShipmentsDaoList;
    }

    public List<PODShipmentsDao> getPodShipmentsDaoList() {
        return podShipmentsDaoList;
    }

    public void setPodShipmentsDaoList(List<PODShipmentsDao> podShipmentsDaoList) {
        this.podShipmentsDaoList = podShipmentsDaoList;
    }



    public List<ShipmentDetailsDao> getShipmentDetailsDaoList() {
        return shipmentDetailsDaoList;
    }

    public void setShipmentDetailsDaoList(List<ShipmentDetailsDao> shipmentDetailsDaoList) {
        this.shipmentDetailsDaoList = shipmentDetailsDaoList;
    }
}
