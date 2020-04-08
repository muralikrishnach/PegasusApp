package com.pegasus.pegasus.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pegasus.pegasus.model.ShipmentDataDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.model.repository.Api;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.pegasus.pegasus.model.repository.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentDetailsViewModel extends ViewModel {

    private MutableLiveData<ShipmentDataDao> shipmentLiveData;
    private MutableLiveData<TrackingDetailsDao> trackingLiveData;

    public MutableLiveData<ShipmentDataDao> getShipmentsData(String WayBillNo){
        if(shipmentLiveData==null){
            shipmentLiveData = new MutableLiveData<>();

            getShipmentsDetails(WayBillNo);
        }
        return shipmentLiveData;
    }

    public MutableLiveData<TrackingDetailsDao> getTrackingLiveData(){
        if(trackingLiveData==null){
            trackingLiveData = new MutableLiveData<>();

            getTrackingDetails();
        }
        return trackingLiveData;
    }

    public void getShipmentsDetails(String WayBillNo){

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("WaybillNumber",WayBillNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api retrofitClient =  RetrofitClient.getClient().create(Api.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonParams.toString());
        Call<ResponseBody> call = retrofitClient.getShipmentDetails(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse( Call<ResponseBody> call,  Response<ResponseBody> response) {
                ShipmentDataDao shipmentDataDao = null;
                try {
                    JsonParsing parsing = new JsonParsing();
                    shipmentDataDao = parsing.getShipmentDetails(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                shipmentLiveData.setValue(shipmentDataDao);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void getTrackingDetails(){

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("WaybillNumber","22462370");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api retrofitClient =  RetrofitClient.getClient().create(Api.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonParams.toString());
        Call<ResponseBody> call = retrofitClient.getTracking(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TrackingDetailsDao trackingDetailsDao = null;
                try {
                    JsonParsing parsing = new JsonParsing();
                    trackingDetailsDao = parsing.getTrackingDetails(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                trackingLiveData.setValue(trackingDetailsDao);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
