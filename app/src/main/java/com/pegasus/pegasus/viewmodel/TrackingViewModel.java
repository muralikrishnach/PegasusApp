package com.pegasus.pegasus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class TrackingViewModel extends ViewModel {

    private MutableLiveData<TrackingDetailsDao> trackingLiveData;


    public MutableLiveData<TrackingDetailsDao> getTrackingLiveData(String WayBillNo){
        if(trackingLiveData==null){

            trackingLiveData = new MutableLiveData<TrackingDetailsDao>();

            getTrackingDetails(WayBillNo);
        }
        return trackingLiveData;
    }

    public void getTrackingDetails(String WayBill){

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("WaybillNumber",WayBill);
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
                if(response!=null){
                    try{
                        JsonParsing jsonParsing = new JsonParsing();
                        trackingDetailsDao = jsonParsing.getTackingData(response.body().string());
                    }catch(Exception n){
                        n.printStackTrace();
                    }
                    trackingLiveData.setValue(trackingDetailsDao);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
