package com.witold.mapapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private String description;
    private int drawableId;
    private double latitude;
    private double longitude;

    public Place(String name, String description, int drawableId, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.drawableId = drawableId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getCoordinatesAsString() {
        return latitude + "," + longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
