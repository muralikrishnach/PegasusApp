package com.pegasus.pegasus.model;

import java.io.Serializable;

public class ShipmentInfoDetailsDao implements Serializable {
    public String getWaybillNumber() {
        return WaybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        WaybillNumber = waybillNumber;
    }

    public String getPickupDateTime() {
        return PickupDateTime;
    }

    public void setPickupDateTime(String pickupDateTime) {
        PickupDateTime = pickupDateTime;
    }

    public String getETADateTime() {
        return ETADateTime;
    }

    public void setETADateTime(String ETADateTime) {
        this.ETADateTime = ETADateTime;
    }

    public String getPODDateTime() {
        return PODDateTime;
    }

    public void setPODDateTime(String PODDateTime) {
        this.PODDateTime = PODDateTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String WaybillNumber = "";
    private String PickupDateTime = "";
    private String ETADateTime = "";
    private String PODDateTime = "";
    private String Status = "";
}
