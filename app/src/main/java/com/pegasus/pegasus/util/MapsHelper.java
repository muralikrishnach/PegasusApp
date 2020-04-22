package com.pegasus.pegasus.util;

import android.util.Log;

import com.pegasus.pegasus.model.CoordinatesDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MapsHelper {
    /**
     * A method to download json Google Directions data from url
     */
    public static String LoadRouteDataAsync(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Error => ", "LoadRouteDataAsync" + e.toString());
        } finally {
            Objects.requireNonNull(iStream).close();
            Objects.requireNonNull(urlConnection).disconnect();
        }
        return data;
    }

    public static String GetDirectionsUrl(List<CoordinatesDao> coordinates) {
        int currentIndex = 0;
        int lastIndex = coordinates.size() - 1;
        String str_origin = null;
        String str_dest = null;
        StringBuilder str_waypoints = new StringBuilder("waypoints=");
        for (CoordinatesDao coordinate : coordinates) {
            if (currentIndex == 0) {
                // Origin of route
                str_origin = "origin=" + coordinate.getLatitude() + "," + coordinate.getLongitude();
            } else if (currentIndex == lastIndex) {
                // Destination of route
                str_dest = "destination=" + coordinate.getLatitude() + "," + coordinate.getLongitude();
            } else {
                // Waypoints
                str_waypoints.append(coordinate.getLatitude()).append(",").append(coordinate.getLongitude()).append("|");
            }
            currentIndex++;
        }

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + str_waypoints;
        // Output format
        String output = "json";
        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters
                + "&key=" + "AIzaSyDXn3Ln9cmvhhMaL1HciFo9LsEq23PoYks";
    }
}