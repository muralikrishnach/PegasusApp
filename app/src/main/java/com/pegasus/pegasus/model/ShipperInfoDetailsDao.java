package com.pegasus.pegasus.model;

import java.io.Serializable;

public class ShipperInfoDetailsDao implements Serializable {

    public String getShipperContactName() {
        return ShipperContactName;
    }

    public void setShipperContactName(String shipperContactName) {
        ShipperContactName = shipperContactName;
    }

    public String getShipperCompanyName() {
        return ShipperCompanyName;
    }

    public void setShipperCompanyName(String shipperCompanyName) {
        ShipperCompanyName = shipperCompanyName;
    }

    public String getShipperAddress1() {
        return ShipperAddress1;
    }

    public void setShipperAddress1(String shipperAddress1) {
        ShipperAddress1 = shipperAddress1;
    }

    public String getShipperAddress2() {
        return ShipperAddress2;
    }

    public void setShipperAddress2(String shipperAddress2) {
        ShipperAddress2 = shipperAddress2;
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

    public String getShipperZipCode() {
        return ShipperZipCode;
    }

    public void setShipperZipCode(String shipperZipCode) {
        ShipperZipCode = shipperZipCode;
    }

    public String getShipperCountry() {
        return ShipperCountry;
    }

    public void setShipperCountry(String shipperCountry) {
        ShipperCountry = shipperCountry;
    }

    private String ShipperContactName = "";
    private String ShipperCompanyName = "";
    private String ShipperAddress1 = "";
    private String ShipperAddress2 = "";
    private String ShipperCity = "";
    private String ShipperState = "";
    private String ShipperZipCode = "";
    private String ShipperCountry = "";
}
