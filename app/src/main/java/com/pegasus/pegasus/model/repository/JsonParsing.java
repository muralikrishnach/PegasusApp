package com.pegasus.pegasus.model.repository;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.ConsigneeInfoDetailsDao;
import com.pegasus.pegasus.model.CoordinatesDao;
import com.pegasus.pegasus.model.DataDao;
import com.pegasus.pegasus.model.LineItemsDao;
import com.pegasus.pegasus.model.LoginDao;
import com.pegasus.pegasus.model.OpenPODShipmentDetailsDao;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.PODShipmentsDao;
import com.pegasus.pegasus.model.ShipmentDataDao;
import com.pegasus.pegasus.model.ShipmentInfoDetailsDao;
import com.pegasus.pegasus.model.ShipperInfoDetailsDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonParsing {

    public static final String Head1 = "OPEN SHIPMMENTS";
    public static final String Head2 = "POD SHIPMMENTS LAST 7 DAYS";

   public LoginDao login(String jsonString)  {
       LoginDao loginDao = null;

       JSONObject job;
       JSONObject jdata;
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

    public OpenPODShipmentDetailsDao openShipments(String jsonString)  {
        OpenPODShipmentDetailsDao openPodShipDao = null;

         String Head1 = "OPEN SHIPMMENTS";
         String  Head2 = "POD SHIPMMENTS LAST 7 DAYS";

        JSONObject job;
        JSONObject jdata;
        JSONArray jOpenArray;
        JSONArray jPODArray;

        Log.v("","jsonString"+jsonString);

        if(jsonString!=null && !jsonString.isEmpty()) {
            openPodShipDao = new OpenPODShipmentDetailsDao();
            List<OpenShipmentsDao> openShipmentsDaoList = new ArrayList<>();
            List<PODShipmentsDao> podShipmentsDaoList = new ArrayList<>();

            try {
                job = new JSONObject(jsonString);
                jdata = job.getJSONObject("data");
                jOpenArray = jdata.getJSONArray("OpenShipments");
                jPODArray  = jdata.getJSONArray("PODShipments");

                int error_number = job.getInt("error_number");
                String error_description = job.getString("error_description");
                int openshipment_count = job.getInt("openshipment_count");
                int podshipment_count = job.getInt("podshipment_count");
                boolean status = job.getBoolean("status");


                if(status){

                    for(int i=0;i<jOpenArray.length();i++){
                        JSONObject jsonObject = jOpenArray.getJSONObject(i);
                        String WaybillNumber = jsonObject.getString("WaybillNumber");
                        String ShipperCity = jsonObject.getString("ShipperCity");
                        String ShipperState = jsonObject.getString("ShipperState");
                        String ConsigneeCity = jsonObject.getString("ConsigneeCity");
                        String ConsigneeState = jsonObject.getString("ConsigneeState");
                        String Status = jsonObject.getString("Status");

                        OpenShipmentsDao data = new OpenShipmentsDao();

                        data.setWaybillNumber(WaybillNumber);
                        data.setShipperCity(ShipperCity);
                        data.setShipperState(ShipperState);
                        data.setConsigneeCity(ConsigneeCity);
                        data.setConsigneeState(ConsigneeState);
                        data.setStatus(Status);

                        openShipmentsDaoList.add(data);
                    }

                    for(int i=0;i<jPODArray.length();i++){
                        JSONObject jsonObject = jPODArray.getJSONObject(i);
                        String WaybillNumber = jsonObject.getString("WaybillNumber");
                        String ShipperCity = jsonObject.getString("ShipperCity");
                        String ShipperState = jsonObject.getString("ShipperState");
                        String ConsigneeCity = jsonObject.getString("ConsigneeCity");
                        String ConsigneeState = jsonObject.getString("ConsigneeState");
                        String Status = jsonObject.getString("Status");

                        PODShipmentsDao data = new PODShipmentsDao();

                        data.setWaybillNumber(WaybillNumber);
                        data.setShipperCity(ShipperCity);
                        data.setShipperState(ShipperState);
                        data.setConsigneeCity(ConsigneeCity);
                        data.setConsigneeState(ConsigneeState);
                        data.setStatus(Status);

                        podShipmentsDaoList.add(data);
                    }

                }

                openPodShipDao.setError_number(error_number);
                openPodShipDao.setError_description(error_description);
                openPodShipDao.setOpenshipment_count(openshipment_count);
                openPodShipDao.setPodshipment_count(podshipment_count);
                openPodShipDao.setStatus(status);
                openPodShipDao.setOpenShipmentsDaoList(openShipmentsDaoList);
                openPodShipDao.setPodShipmentsDaoList(podShipmentsDaoList);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return openPodShipDao;
    }

    public ShipmentDataDao getShipmentDetails(String jsonString){
       ShipmentDataDao shipmentDataDao = null;

        JSONObject job = null;
        JSONObject jdata = null;
        JSONObject jshipInfo = null;
        JSONObject jshiperInfo = null;
        JSONObject jconsignInfo = null;
        JSONArray jsonLineArray = null;

        if(jsonString!=null && !jsonString.isEmpty()) {
            shipmentDataDao = new ShipmentDataDao();
            try {
                job = new JSONObject(jsonString);
                jdata = job.getJSONObject("data");

                int error_number = job.getInt("error_number");
                String error_description = job.getString("error_description");
                int count = job.getInt("count");
                boolean status = job.getBoolean("status");

                List<ShipmentInfoDetailsDao> shipmentInfoDetailsDaoList = new ArrayList<>();
                List<ShipperInfoDetailsDao> shipperInfoDetailsDaoList = new ArrayList<>();
                List<ConsigneeInfoDetailsDao> consigneeInfoDetailsDaoList = new ArrayList<>();
                List<LineItemsDao> lineItemsDaoList = new ArrayList<>();

                if(status){
                    jshipInfo = jdata.getJSONObject("ShipmentInfoDetails");
                    jshiperInfo = jdata.getJSONObject("ShipperInfoDetails");
                    jconsignInfo = jdata.getJSONObject("ConsigneeInfoDetails");
                    jsonLineArray = jdata.getJSONArray("LineItems");

                    //ShipmentInfoDetails
                    ShipmentInfoDetailsDao shipmentInfoDetailsDao = new ShipmentInfoDetailsDao();
                    shipmentInfoDetailsDao.setWaybillNumber(jshipInfo.getString("WaybillNumber"));
                    shipmentInfoDetailsDao.setPickupDateTime(jshipInfo.getString("PickupDateTime"));
                    shipmentInfoDetailsDao.setETADateTime(jshipInfo.getString("ETADateTime"));
                    shipmentInfoDetailsDao.setPODDateTime(jshipInfo.getString("PODDateTime"));
                    shipmentInfoDetailsDao.setStatus(jshipInfo.getString("Status"));
                    shipmentInfoDetailsDaoList.add(shipmentInfoDetailsDao);

                    //ShipperInfoDetails
                    ShipperInfoDetailsDao shipperInfoDetailsDao = new ShipperInfoDetailsDao();
                    shipperInfoDetailsDao.setShipperContactName(jshiperInfo.getString("ShipperContactName"));
                    shipperInfoDetailsDao.setShipperCompanyName(jshiperInfo.getString("ShipperCompanyName"));
                    shipperInfoDetailsDao.setShipperAddress1(jshiperInfo.getString("ShipperAddress1"));
                    shipperInfoDetailsDao.setShipperAddress2(jshiperInfo.getString("ShipperAddress2"));
                    shipperInfoDetailsDao.setShipperCity(jshiperInfo.getString("ShipperCity"));
                    shipperInfoDetailsDao.setShipperState(jshiperInfo.getString("ShipperState"));
                    shipperInfoDetailsDao.setShipperZipCode(jshiperInfo.getString("ShipperZipCode"));
                    shipperInfoDetailsDaoList.add(shipperInfoDetailsDao);

                    //ConsigneeInfoDetails
                    ConsigneeInfoDetailsDao consigneeInfoDetailsDao = new ConsigneeInfoDetailsDao();
                    consigneeInfoDetailsDao.setConsigneeContactName(jconsignInfo.getString("ConsigneeContactName"));
                    consigneeInfoDetailsDao.setConsigneeCompanyName(jconsignInfo.getString("ConsigneeCompanyName"));
                    consigneeInfoDetailsDao.setConsigneeAddress1(jconsignInfo.getString("ConsigneeAddress1"));
                    consigneeInfoDetailsDao.setConsigneeAddress2(jconsignInfo.getString("ConsigneeAddress2"));
                    consigneeInfoDetailsDao.setConsigneeCity(jconsignInfo.getString("ConsigneeCity"));
                    consigneeInfoDetailsDao.setConsigneeState(jconsignInfo.getString("ConsigneeState"));
                    consigneeInfoDetailsDao.setConsigneeZipCode(jconsignInfo.getString("ConsigneeZipCode"));
                    consigneeInfoDetailsDao.setConsigneeCountry(jconsignInfo.getString("ConsigneeCountry"));
                    consigneeInfoDetailsDaoList.add(consigneeInfoDetailsDao);


                    for (int i=0;i<jsonLineArray.length();i++){

                        JSONObject jsonObject = jsonLineArray.getJSONObject(i);
                        LineItemsDao lineItemsDao = new LineItemsDao();

                        lineItemsDao.setPieces(jsonObject.getString("Pieces"));
                        lineItemsDao.setDescription(jsonObject.getString("Description"));
                        lineItemsDao.setWeight(jsonObject.getString("Weight"));

                        lineItemsDaoList.add(lineItemsDao);
                    }


                }

                shipmentDataDao.setError_number(error_number);
                shipmentDataDao.setError_description(error_description);
                shipmentDataDao.setCount(count);
                shipmentDataDao.setStatus(status);
                shipmentDataDao.setShipmentInfoDetailsDaoList(shipmentInfoDetailsDaoList);
                shipmentDataDao.setShipperInfoDetailsDaoList(shipperInfoDetailsDaoList);
                shipmentDataDao.setConsigneeInfoDetailsDaoList(consigneeInfoDetailsDaoList);
                shipmentDataDao.setLineItemsDaoList(lineItemsDaoList);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
       return shipmentDataDao;
    }


    public TrackingDetailsDao getTackingData(String jsonString){
       TrackingDetailsDao trackingDetailsDao = null;

       if(jsonString!=null && !jsonString.isEmpty()){
           JSONObject job;
           JSONArray jsonArray;

           try {
               job = new JSONObject(jsonString);
               jsonArray = job.getJSONArray("data");

               trackingDetailsDao = new TrackingDetailsDao();
               List<CoordinatesDao> coordinatesDaoList = new ArrayList<>();

               trackingDetailsDao.setError_number(job.getInt("error_number"));
               trackingDetailsDao.setError_description(job.getString("error_description"));
               trackingDetailsDao.setCurrentLocationLatitude(job.getString("currentLocationLatitude"));
               trackingDetailsDao.setCurrentLocationLongitude(job.getString("currentLocationLongitude"));
               trackingDetailsDao.setOriginLocationColor(job.getString("originLocationColor"));
               trackingDetailsDao.setDestinationLocationColor(job.getString("destinationLocationColor"));
               trackingDetailsDao.setCurrentLocationColor(job.getString("currentLocationColor"));
               trackingDetailsDao.setCount(job.getInt("count"));
               trackingDetailsDao.setStatus(job.getBoolean("status"));

               boolean Status = job.getBoolean("status");

               if(Status){
                   for(int i=0;i<jsonArray.length();i++){
                       JSONObject job1 = jsonArray.getJSONObject(i);

                       CoordinatesDao coordinatesDao  = new CoordinatesDao();
                       coordinatesDao.setLatitude(job1.getString("Latitude"));
                       coordinatesDao.setLongitude(job1.getString("Longitude"));
                       coordinatesDao.setTime(job1.getString("Time"));
                       coordinatesDao.setIconColor(job1.getString("IconColor"));
                       coordinatesDaoList.add(coordinatesDao);
                   }
               }

               trackingDetailsDao.setCoordinatesDaoList(coordinatesDaoList);



           } catch (JSONException e) {
               e.printStackTrace();
           }

       }


       return trackingDetailsDao;
    }

}
