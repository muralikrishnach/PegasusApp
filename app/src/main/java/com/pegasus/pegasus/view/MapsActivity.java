package com.pegasus.pegasus.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static GoogleMap mMap;
    static TrackingDetailsDao trackingDetailsDao;

    private AppCompatImageButton imgPower;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            trackingDetailsDao = (TrackingDetailsDao) getIntent().getExtras().getSerializable("Tracking");
        }
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        findViewById(R.id.imgLogout).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        // Getting URL to the Google Directions API
        String mapsKey = getResources().getString(R.string.google_maps_key);
        String url = MapsHelper.GetDirectionsUrl(trackingDetailsDao.getCoordinatesDaoList(), mapsKey);
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgLogout:
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


                break;
        }
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

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            Marker mMarker;

            int currentIndex = 0;
            int lastIndex = trackingDetailsDao.getCoordinatesDaoList().size() - 1;
            for (CoordinatesDao coordinate : trackingDetailsDao.getCoordinatesDaoList()) {
                if (currentIndex == 0 || currentIndex == lastIndex) {
                    mMarker = AddMarker(coordinate.getLatitude(), coordinate.getLongitude(), coordinate.getIconColor());
                } else {
                    mMarker = AddDot(coordinate.getLatitude(), coordinate.getLongitude(), coordinate.getIconColor());
                }
                builder.include(mMarker.getPosition());
                currentIndex++;
            }
            mMarker = AddMarker(trackingDetailsDao.getCurrentLocationLatitude(),
                    trackingDetailsDao.getCurrentLocationLongitude(),
                    trackingDetailsDao.getCurrentLocationColor());
            builder.include(mMarker.getPosition());

            /**initialize the padding for map boundary*/
            int padding = 150;
            /**create the bounds from latlngBuilder to set into map camera*/
            LatLngBounds bounds = builder.build();
            /**create the camera with bounds and padding to set into map*/
            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            /**call the map call back to know map is loaded or not*/
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    /**set animated zoom camera into map*/
                    mMap.animateCamera(cu);

                }
            });
            /*mMap.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(Double.parseDouble(trackingDetailsDao.getCurrentLocationLatitude()),
                            Double.parseDouble(trackingDetailsDao.getCurrentLocationLongitude()))));*/
        }
    }

    public static Marker AddMarker(String latitude, String longitude, String iconColor) {
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                .rotation((float) 3.5)
                .icon(GetMarkerIcon(iconColor)));
    }

    public static Marker AddDot(String latitude, String longitude, String iconColor) {
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                .rotation((float) 3.5)
                .icon(vectorByColor(iconColor)));
    }

    private static BitmapDescriptor vectorByColor(String color) {
        switch (color) {
            case "blue":
                return vectorToBitmap(R.drawable.map_marker_blue, ContextCompat.getColor(context, R.color.darkblue));
            case "red":
                return vectorToBitmap(R.drawable.map_marker_red, ContextCompat.getColor(context, R.color.red));
            case "green":
                return vectorToBitmap(R.drawable.map_marker_green, ContextCompat.getColor(context, R.color.green));
            case "yellow":
                return vectorToBitmap(R.drawable.map_marker_yellow, ContextCompat.getColor(context, R.color.yellow));
            default:
                return vectorToBitmap(R.drawable.map_marker_red, ContextCompat.getColor(context, R.color.red));
        }
    }

    private static BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(context.getResources(), id, null);
        assert vectorDrawable != null;
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
