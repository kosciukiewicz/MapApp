package com.witold.mapapp.model;

import java.io.Serializable;
import java.util.List;

public class Trip implements Serializable {
    public static final String BICYCLE_TRIP = "Rowerowa";
    public static final String CLASSIC_TRIP = "Piesza";

    private String name;
    private String description;
    private String type;
    private int estimatedTime;
    private int imagePlaceholder;
    private List<Place> placeList;
    private Place startPlace;
    private Place destinationPlace;

    public Trip(String name, String description, String type, int estimatedTime, int imagePlaceholder, List<Place> placeList, Place startPlace, Place destinationPlace) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.estimatedTime = estimatedTime;
        this.imagePlaceholder = imagePlaceholder;
        this.placeList = placeList;
        this.startPlace = startPlace;
        this.destinationPlace = destinationPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getImagePlaceholder() {
        return imagePlaceholder;
    }

    public void setImagePlaceholder(int imagePlaceholder) {
        this.imagePlaceholder = imagePlaceholder;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public Place getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(Place startPlace) {
        this.startPlace = startPlace;
    }

    public Place getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(Place destinationPlace) {
        this.destinationPlace = destinationPlace;
    }
}
