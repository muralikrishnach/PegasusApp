package com.pegasus.pegasus.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pegasus.pegasus.model.LoginDao;
import com.pegasus.pegasus.model.LoginValidations;
import com.pegasus.pegasus.model.repository.Api;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.pegasus.pegasus.model.repository.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginDao> loginResponse;
    private LoginValidations userdata;
    private Context context;
    private Gson gson;

    public MutableLiveData<LoginDao> checkLogin() {

        if (loginResponse == null) {
            loginResponse = new MutableLiveData<LoginDao>();

            checkLoginResponse();
        }
        return loginResponse;
    }

    private void checkLoginResponse() {

        if(!userdata.isValidUserName())
        {
            Toast.makeText(context, "Enter LoginID", Toast.LENGTH_SHORT).show();
        }
        else if(!userdata.isValidPassword())
        {
            Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("Web_User_Login_ID",userdata.getUserName());
                jsonParams.put("Password",userdata.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Api retrofitClient =  RetrofitClient.getClient().create(Api.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonParams.toString());
            Call<ResponseBody> call = retrofitClient.checkLogin(body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    LoginDao login = null;
                    try {
                        JsonParsing parsing = new JsonParsing();
                        login = parsing.login(response.body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loginResponse.setValue(login);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }



    }

    public boolean isValidUserName(){
        if(this.userdata.getUserName()==null || this.userdata.getUserName().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public boolean isValidPassword(){
        if(this.userdata.getPassword()==null || this.userdata.getPassword().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void setUserdata(LoginValidations userdata) {
        this.userdata = userdata;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
