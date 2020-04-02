package com.pegasus.pegasus.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pegasus.pegasus.model.LoginDao;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.PODShipmentsDao;
import com.pegasus.pegasus.model.OpenPODShipmentDetailsDao;
import com.pegasus.pegasus.model.repository.Api;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.pegasus.pegasus.model.repository.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenShipMentViewModel extends ViewModel {

    MutableLiveData<OpenPODShipmentDetailsDao> shipLiveData;

    public MutableLiveData<OpenPODShipmentDetailsDao> getShipmentDetails() {

        if (shipLiveData == null) {

            shipLiveData = new MutableLiveData<>();

            loadShipMents();

        }
        return shipLiveData;
    }

    private void loadShipMents() {

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("BillNo","165309");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api retrofitClient =  RetrofitClient.getClient().create(Api.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonParams.toString());
        Call<ResponseBody> call = retrofitClient.getOpenShipents(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                OpenPODShipmentDetailsDao openPODShipmentDetailsDao = null;
                try {
                    JsonParsing parsing = new JsonParsing();
                    openPODShipmentDetailsDao = parsing.openShipments(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                shipLiveData.setValue(openPODShipmentDetailsDao);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }



}
