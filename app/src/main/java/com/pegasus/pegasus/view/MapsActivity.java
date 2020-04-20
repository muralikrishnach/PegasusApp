package com.pegasus.pegasus.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.CoordinatesDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.model.repository.DirectionDataParsing;


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
import java.util.Iterator;
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

    private AppCompatImageButton imgback,imgPower;

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

        imgback = findViewById(R.id.imgBack);
        imgPower = findViewById(R.id.imgLogout);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MapsActivity.this);
                alert.setMessage("Are you sure you want to Logout?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.clear();

        MarkerOptions options;
        PolylineOptions polylineOptions = new PolylineOptions();

        CircleOptions circleOptions = new CircleOptions();

        options = new MarkerOptions();
        double lat1 = Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude());
        double lang1 = Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude());
        LatLng latLng = new LatLng(lat1,lang1);
        options.position(latLng);
//        options.title("Source");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        polylineOptions.add(latLng);
        mMap.addMarker(options);

        for(int i=1;i<trackingDetailsDao.getCoordinatesDaoList().size();i++){
            // Fetching i-th route
            CoordinatesDao path = trackingDetailsDao.getCoordinatesDaoList().get(i);
            // Fetching all the points in i-th route
            double latt = Double.parseDouble(path.getLongitude());
            double langi = Double.parseDouble(path.getLongitude());
            LatLng position = new LatLng(latt, langi);
            options.position(position);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(options);
            polylineOptions.add(position);
            circleOptions.center(position);
        }
        double destLat = Double.parseDouble(trackingDetailsDao.getCoordinatesDaoList().get(0).getLatitude());
        double destLong = Double.parseDouble(trackingDetailsDao.getCoordinatesDaoList().get(0).getLongitude());
        LatLng destLatLng = new LatLng(destLat,destLong);
        options.position(destLatLng);
//        options.title("Destination");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(options);
//        polylineOptions.add(destLatLng);


       mMap.addPolyline(polylineOptions
                .clickable(true)
//                .color((Color.BLUE))
                .color(getResources().getColor(R.color.header))
                .width(8))
                .setJointType(JointType.ROUND);

       mMap.addCircle(circleOptions
               .radius(1000)
               .strokeWidth(10)
               .strokeColor(Color.RED)
               .fillColor(Color.argb(128, 255, 0, 0))
               .clickable(true));


        /*mMap.addCircle(new CircleOptions()
                                .center(latLng)
                                .strokeColor(Color.RED)
                                .strokeWidth(3f)
                                .fillColor(Color.argb(70,150,50,50))
                                .radius(400.0));*/

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {

            }
        });

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,lang1), 4));



        // Checks, whether start and end locations are captured
//        if(mMarkerPoints.size() >= 2){
           /* mOrigin = mMarkerPoints.get(0);
            mDestination = mMarkerPoints.get(1);*/
//            drawRoute();
//        }


    }

    private void drawRoute(){

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

        /*ArrayList<LatLng> points = null;
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

            */



    }




    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        Log.v("Google Maps Url---","Google Maps Url---"+url);

        return url;
    }


/** A method to download json data from url */
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



    /** A class to download data from Google Directions URL */
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



    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionDataParsing parser = new DirectionDataParsing();

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
            if(result!=null) {
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

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
    }

}
