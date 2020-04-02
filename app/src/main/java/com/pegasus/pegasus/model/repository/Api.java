package com.pegasus.pegasus.model.repository;


import com.pegasus.pegasus.model.LoginDao;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://nitest.pegasuslogistics.com/MobileService/";


    @Headers("Content-Type: application/json")
    @POST("UserLogin")
    Call<ResponseBody> checkLogin(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("GetShipmentList")
    Call<ResponseBody> getOpenShipents(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("GetShipmentInfo")
    Call<ResponseBody> getShipmentDetails(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("GetTrackingDetails")
    Call<ResponseBody> getTracking(@Body RequestBody requestBody);



}
