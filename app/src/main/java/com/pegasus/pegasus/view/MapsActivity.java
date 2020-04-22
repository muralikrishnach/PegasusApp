package com.pegasus.pegasus.view;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.CoordinatesDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.util.DirectionsJSONParser;
import com.pegasus.pegasus.util.MapsHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    static TrackingDetailsDao trackingDetailsDao;

    private AppCompatImageButton imgPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            trackingDetailsDao = (TrackingDetailsDao) getIntent().getExtras().getSerializable("Tracking");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        imgPower = findViewById(R.id.imgLogout);
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        // Getting URL to the Google Directions API
        String url = MapsHelper.GetDirectionsUrl(trackingDetailsDao.getCoordinatesDaoList());
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.clear(); //clear old markers
    }

    // Fetches data from url passed
    private static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = MapsHelper.LoadRouteDataAsync(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private static class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    if (point != null && point.get("lat") != null && point.get("lng") != null) {
                        double lat = Double.parseDouble(Objects.requireNonNull(point.get("lat")));
                        double lng = Double.parseDouble(Objects.requireNonNull(point.get("lng")));
                        LatLng position = new LatLng(lat, lng);
                        points.add(position);
                    }
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
            }
            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

            for (CoordinatesDao coordinate : trackingDetailsDao.getCoordinatesDaoList()) {
                AddMarker(coordinate.getLatitude(), coordinate.getLongitude(), coordinate.getIconColor());
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude()),
                            Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude()))));
        }
    }

    public static void AddMarker(String latitude, String longitude, String iconColor) {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                .rotation((float) 3.5)
                .icon(GetMarkerIcon(iconColor)));
    }

    // method definition
    public static BitmapDescriptor GetMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(GetMarkerColorCode(color)), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    public static String GetMarkerColorCode(String color) {
        switch (color) {
            case "blue":
                return "#0000FF";
            case "red":
                return "#8B0000";
            case "green":
                return "#008000";
            case "yellow":
                return "#FFFF00";
            default:
                return "#FF0000";
        }
    }


}
