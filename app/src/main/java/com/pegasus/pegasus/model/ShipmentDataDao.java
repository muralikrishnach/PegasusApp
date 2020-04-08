package com.pegasus.pegasus.model;

import java.util.ArrayList;
import java.util.List;

public class ShipmentDataDao {

    private int error_number = 0;

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

    public List<LineItemsDao> getLineItemsDaoList() {
        return lineItemsDaoList;
    }

    public void setLineItemsDaoList(List<LineItemsDao> lineItemsDaoList) {
        this.lineItemsDaoList = lineItemsDaoList;
    }

    private String error_description = "";
    private int count = 0;
    private boolean status = false;
    private List<ShipmentInfoDetailsDao> shipmentInfoDetailsDaoList = new ArrayList<>();

    public List<ShipmentInfoDetailsDao> getShipmentInfoDetailsDaoList() {
        return shipmentInfoDetailsDaoList;
    }

    public void setShipmentInfoDetailsDaoList(List<ShipmentInfoDetailsDao> shipmentInfoDetailsDaoList) {
        this.shipmentInfoDetailsDaoList = shipmentInfoDetailsDaoList;
    }

    public List<ShipperInfoDetailsDao> getShipperInfoDetailsDaoList() {
        return shipperInfoDetailsDaoList;
    }

    public void setShipperInfoDetailsDaoList(List<ShipperInfoDetailsDao> shipperInfoDetailsDaoList) {
        this.shipperInfoDetailsDaoList = shipperInfoDetailsDaoList;
    }

    public List<ConsigneeInfoDetailsDao> getConsigneeInfoDetailsDaoList() {
        return consigneeInfoDetailsDaoList;
    }

    public void setConsigneeInfoDetailsDaoList(List<ConsigneeInfoDetailsDao> consigneeInfoDetailsDaoList) {
        this.consigneeInfoDetailsDaoList = consigneeInfoDetailsDaoList;
    }

    private List<ShipperInfoDetailsDao> shipperInfoDetailsDaoList = new ArrayList<>();
    private List<ConsigneeInfoDetailsDao> consigneeInfoDetailsDaoList = new ArrayList<>();
    private List<LineItemsDao> lineItemsDaoList = new ArrayList<>();






}
