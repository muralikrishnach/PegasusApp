package com.pegasus.pegasus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginDao implements Serializable {

    private DataDao dataDao;

    public DataDao getDataDao() {
        return dataDao;
    }

    public void setDataDao(DataDao dataDao) {
        this.dataDao = dataDao;
    }

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

    private int error_number = 0;
    private String error_description = "";
    private int count = 0;

    private boolean status ;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
