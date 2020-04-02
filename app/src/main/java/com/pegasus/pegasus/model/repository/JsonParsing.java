package com.pegasus.pegasus.model.repository;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pegasus.pegasus.model.DataDao;
import com.pegasus.pegasus.model.LoginDao;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonParsing {

   public LoginDao login(String jsonString)  {
       LoginDao loginDao = null;

       JSONObject job = null;
       JSONObject jdata = null;
       Log.v("","jsonString"+jsonString);

       if(jsonString!=null && !jsonString.isEmpty()) {
           try {
               job = new JSONObject(jsonString);


               boolean status = job.getBoolean("status");

               int error_number = job.getInt("error_number");
               String error_description = job.getString("error_description");
               int count = job.getInt("count");
               String BillNo = "";
               if(status){
                   jdata = job.getJSONObject("data");
                   BillNo = jdata.getString("BillNo");
               }

               Log.v("","BillNo"+BillNo);
               Log.v("","status"+status);

               DataDao data = new DataDao();
               data.setBillNo(BillNo);
               loginDao = new LoginDao();
               loginDao.setDataDao(data);
               loginDao.setError_number(error_number);
               loginDao.setError_description(error_description);
               loginDao.setCount(count);
               loginDao.setStatus(status);

           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
       return loginDao;
   }

}
