package com.pegasus.pegasus.model;

public class PODShipmentsDao {

    public String getWaybillNumber() {
        return WaybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        WaybillNumber = waybillNumber;
    }

    public String getShipperCity() {
        return ShipperCity;
    }

    public void setShipperCity(String shipperCity) {
        ShipperCity = shipperCity;
    }

    public String getShipperState() {
        return ShipperState;
    }

    public void setShipperState(String shipperState) {
        ShipperState = shipperState;
    }

    public String getConsigneeCity() {
        return ConsigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        ConsigneeCity = consigneeCity;
    }

    public String getConsigneeState() {
        return ConsigneeState;
    }

    public void setConsigneeState(String consigneeState) {
        ConsigneeState = consigneeState;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String WaybillNumber = "";
    private String ShipperCity = "";
    private String ShipperState = "";
    private String ConsigneeCity = "";
    private String ConsigneeState = "";
    private String Status = "";
}
