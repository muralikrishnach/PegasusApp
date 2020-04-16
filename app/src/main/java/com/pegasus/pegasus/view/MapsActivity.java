package com.pegasus.pegasus.view;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.CoordinatesDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    ArrayList<LatLng> mMarkerPoints;

    private static final int PATTERN_GAP_LENGTH_PX = 10;  // 1
    private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final Dot DOT = new Dot();
    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);

    TrackingDetailsDao trackingDetailsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getIntent().getExtras()!=null){
            trackingDetailsDao = (TrackingDetailsDao) getIntent().getExtras().getSerializable("Tracking");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mMarkerPoints = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*PolylineOptions lineOptions = null;

        lineOptions = new PolylineOptions();
        for(int i=0;i<trackingDetailsDao.getCoordinatesDaoList().size();i++){
            // Fetching i-th route
            CoordinatesDao path = trackingDetailsDao.getCoordinatesDaoList().get(i);
            // Fetching all the points in i-th route
            double lat = Double.parseDouble(path.getLongitude());
            double lng = Double.parseDouble(path.getLongitude());
            LatLng position = new LatLng(lat, lng);

            mMarkerPoints.add(position);

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(mMarkerPoints);
            lineOptions.color(getResources().getColor(R.color.green));
            lineOptions.width(12);
            lineOptions.clickable(true);
            lineOptions.pattern(PATTERN_DOTTED);


        }

        // Drawing polyline in the Google Map for the i-th route
        if(lineOptions != null) {
            if(mPolyline != null){
                mPolyline.remove();
            }
            mPolyline = mMap.addPolyline(lineOptions);

            double lat = Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude());
            double lng = Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude());

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 4));

        }else
            Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();*/

          /*if(i==0) {
                MarkerOptions options = new MarkerOptions();
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mMap.addMarker(options);
            }else if(i == trackingDetailsDao.getCoordinatesDaoList().size()) {
                MarkerOptions options = new MarkerOptions();
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(options);
            }*/


        for(int i=0;i<trackingDetailsDao.getCoordinatesDaoList().size();i++){
            // Fetching i-th route
            CoordinatesDao path = trackingDetailsDao.getCoordinatesDaoList().get(i);
            // Fetching all the points in i-th route
            double lat = Double.parseDouble(path.getLongitude());
            double lng = Double.parseDouble(path.getLongitude());
            LatLng position = new LatLng(lat, lng);

            mMarkerPoints.add(position);
        }

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(Color.RED)
                .pattern(PATTERN_DOTTED)
                .addAll(mMarkerPoints));

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        double lat = Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude());
        double lang = Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lang), 4));


       /* double geolat1 = 0.0;
        double geolang1 = 0.0;
        double geolat2 = 0.0;
        double geolang2 = 0.0;

        geolat1 = Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude());
        geolang1 = Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude());

        geolat2 = Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude());
        geolang2 = Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude());

        LatLng latLng1 = new LatLng(geolat1,geolang1);
        LatLng latLng2 = new LatLng(geolat2,geolang2);

        mMarkerPoints.add(latLng1);
        mMarkerPoints.add(latLng2);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(latLng1);
        options.position(latLng2);

        if(mMarkerPoints.size()==1){
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }else if(mMarkerPoints.size()==2){
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        // Add new marker to the Google Map Android API V2
        mMap.addMarker(options);*/

        /*// Checks, whether start and end locations are captured
        if(mMarkerPoints.size() >= 2){
           *//* mOrigin = mMarkerPoints.get(0);
            mDestination = mMarkerPoints.get(1);*//*
            drawRoute();
        }*/


    }

    private void drawRoute(){

        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;

        // Traversing through all the routes
        lineOptions = new PolylineOptions();

        for(int i=0;i<trackingDetailsDao.getCoordinatesDaoList().size();i++){
            points = new ArrayList<LatLng>();
            // Fetching i-th route
            CoordinatesDao path = trackingDetailsDao.getCoordinatesDaoList().get(i);
            // Fetching all the points in i-th route
                double lat = Double.parseDouble(path.getLongitude());
                double lng = Double.parseDouble(path.getLongitude());
                LatLng position = new LatLng(lat, lng);

                points.add(position);

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(8);
            lineOptions.color(Color.RED);
            lineOptions.pattern(PATTERN_DOTTED); //

        }

        // Drawing polyline in the Google Map for the i-th route
        if(lineOptions != null) {
            if(mPolyline != null){
                mPolyline.remove();
            }
            mPolyline = mMap.addPolyline(lineOptions);

        }else
            Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();


    }

     /*// Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);*/


/*    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&amp;"+str_dest+"&amp;"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        Log.v("Google Maps Url---","Google Maps Url---"+url);

        return url;
    }

    *//** A method to download json data from url *//*
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    *//** A class to download data from Google Directions URL *//*
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    *//** A class to parse the Google Directions in JSON format *//*
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }*/

}
