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

    public ShipmentInfoDetailsDao getShipmentInfoDetailsDao() {
        return shipmentInfoDetailsDao;
    }

    public void setShipmentInfoDetailsDao(ShipmentInfoDetailsDao shipmentInfoDetailsDao) {
        this.shipmentInfoDetailsDao = shipmentInfoDetailsDao;
    }

    public ShipperInfoDetailsDao getShipperInfoDetailsDao() {
        return shipperInfoDetailsDao;
    }

    public void setShipperInfoDetailsDao(ShipperInfoDetailsDao shipperInfoDetailsDao) {
        this.shipperInfoDetailsDao = shipperInfoDetailsDao;
    }

    public ConsigneeInfoDetailsDao getConsigneeInfoDetailsDao() {
        return consigneeInfoDetailsDao;
    }

    public void setConsigneeInfoDetailsDao(ConsigneeInfoDetailsDao consigneeInfoDetailsDao) {
        this.consigneeInfoDetailsDao = consigneeInfoDetailsDao;
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
    private ShipmentInfoDetailsDao shipmentInfoDetailsDao = new ShipmentInfoDetailsDao();
    private ShipperInfoDetailsDao shipperInfoDetailsDao = new ShipperInfoDetailsDao();
    private ConsigneeInfoDetailsDao consigneeInfoDetailsDao = new ConsigneeInfoDetailsDao();
    private List<LineItemsDao> lineItemsDaoList = new ArrayList<>();






}
