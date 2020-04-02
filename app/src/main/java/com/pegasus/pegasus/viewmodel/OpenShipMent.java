package com.pegasus.pegasus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pegasus.pegasus.model.LoginDao;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.PODShipmentsDao;
import com.pegasus.pegasus.model.ShipmentDetailsDao;

import java.util.List;

public class OpenShipMent extends ViewModel {

    MutableLiveData<List<OpenShipmentsDao>> openLiveData;
    MutableLiveData<List<PODShipmentsDao>> podLiveData;
    MutableLiveData<List<ShipmentDetailsDao>> shipLiveData;

    public MutableLiveData<List<ShipmentDetailsDao>> getShipmentDetails() {

        if (shipLiveData == null) {

            shipLiveData = new MutableLiveData<>();
        }
        return shipLiveData;
    }



}
