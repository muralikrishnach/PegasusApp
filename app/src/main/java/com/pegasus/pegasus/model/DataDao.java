package com.pegasus.pegasus.model;

import java.io.Serializable;

public class DataDao implements Serializable {

    private String BillNo = "";

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }
}
