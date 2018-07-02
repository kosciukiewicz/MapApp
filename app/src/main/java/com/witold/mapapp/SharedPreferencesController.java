package com.witold.mapapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import timber.log.Timber;

public class SharedPreferencesController {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesController(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.gson = new Gson();
    }

    public int getMapTypeFromSharedPrefereneces() {
        int result = GoogleMap.MAP_TYPE_NORMAL;
        switch (sharedPreferences.getString("map_type", "Normalna")) {
            case "Normalna":
                result = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case "Satelitarna":
                result = GoogleMap.MAP_TYPE_SATELLITE;
                break;
            case "Hybrydowa":
                result = GoogleMap.MAP_TYPE_HYBRID;
                break;
            case "Terenowa":
                result = GoogleMap.MAP_TYPE_TERRAIN;
                break;
        }
        return result;
    }

    public String getMapStyleFromSharedPreferences() {
        try {
            int rawResourceId = R.raw.standard;
            switch (sharedPreferences.getString("map_layout", "Standard")) {
                case "Retro":
                    rawResourceId = R.raw.retro;
                    break;
                case "Silver":
                    rawResourceId = R.raw.silver;
                    break;
                case "Night":
                    rawResourceId = R.raw.night;
                    break;
                case "Aubergine":
                    rawResourceId = R.raw.aubergine;
                    break;
            }

            JSONArray jsonArray = new JSONArray(getJsonFromRaw(rawResourceId));
            boolean found = false;

            if (!sharedPreferences.getBoolean("show_smaller_roads", true)) {
                for (int i = 0; i < jsonArray.length() && !found; i++) {
                    if (jsonArray.getJSONObject(i).has("featureType") && jsonArray.getJSONObject(i).getString("featureType").equals("road.local")) {
                        found = true;
                        JSONArray stylers = jsonArray.getJSONObject(i).getJSONArray("stylers");
                        boolean visibilityFound = false;
                        for (int j = 0; j < jsonArray.length() && !visibilityFound; i++) {
                            if (stylers.getJSONObject(j).has("visibility")) {
                                stylers.getJSONObject(j).remove("visibility");
                                stylers.getJSONObject(j).put("visibility", "off");
                                visibilityFound = true;
                            }
                        }

                        if (!visibilityFound) {
                            stylers.put(new JSONObject("{\"visibility\": \"off\"}"));
                        }
                    }
                }

                if (!found) {
                    jsonArray.put(new JSONObject(getJsonFromRaw(R.raw.local_roads_invisible)));

                }
            }
            Timber.d(jsonArray.toString());
            return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(e);
        }

        return "[]";
    }

    private String getJsonFromRaw(int rawResourceId) {
        InputStream is = context.getResources().openRawResource(rawResourceId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                Timber.e(e);
            }
        }
        return writer.toString();
    }

    public int getRouteColorFromSharedPreferences() {
        switch (sharedPreferences.getString("route_color", "Pomarańczowy")) {
            case "Pomarańczowy":
                return ContextCompat.getColor(context, R.color.colorAccent);
            case "Niebieski":
                return ContextCompat.getColor(context, R.color.colorBlue);
            case "Fioletowy":
                return ContextCompat.getColor(context, R.color.colorViolet);
        }
        return ContextCompat.getColor(context, R.color.colorAccent);
    }

    public int getRouteFlagMarkerIdFromSharedPreferences() {
        switch (sharedPreferences.getString("route_color", "Pomarańczowy")) {
            case "Pomarańczowy":
                return R.drawable.ic_flag_marker_orange;
            case "Niebieski":
                return R.drawable.ic_flag_marker_blue;
            case "Fioletowy":
                return R.drawable.ic_flag_marker_violet;
        }
        return R.drawable.ic_flag_marker_orange;
    }
}
