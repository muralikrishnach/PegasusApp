package com.pegasus.pegasus.model;

import java.io.Serializable;

public class ConsigneeInfoDetailsDao implements Serializable {

    public String getConsigneeContactName() {
        return ConsigneeContactName;
    }

    public void setConsigneeContactName(String consigneeContactName) {
        ConsigneeContactName = consigneeContactName;
    }

    public String getConsigneeCompanyName() {
        return ConsigneeCompanyName;
    }

    public void setConsigneeCompanyName(String consigneeCompanyName) {
        ConsigneeCompanyName = consigneeCompanyName;
    }

    public String getConsigneeAddress1() {
        return ConsigneeAddress1;
    }

    public void setConsigneeAddress1(String consigneeAddress1) {
        ConsigneeAddress1 = consigneeAddress1;
    }

    public String getConsigneeAddress2() {
        return ConsigneeAddress2;
    }

    public void setConsigneeAddress2(String consigneeAddress2) {
        ConsigneeAddress2 = consigneeAddress2;
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

    public String getConsigneeZipCode() {
        return ConsigneeZipCode;
    }

    public void setConsigneeZipCode(String consigneeZipCode) {
        ConsigneeZipCode = consigneeZipCode;
    }

    public String getConsigneeCountry() {
        return ConsigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        ConsigneeCountry = consigneeCountry;
    }

    private String ConsigneeContactName = "";
        private String ConsigneeCompanyName = "";
        private String ConsigneeAddress1 =  "";
        private String ConsigneeAddress2 = "";
        private String ConsigneeCity = "";
        private String ConsigneeState = "";
        private String ConsigneeZipCode = "";
        private String ConsigneeCountry = "";

}
