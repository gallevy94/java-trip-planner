package com.handson.trip_planner.model;

import com.google.maps.model.LatLng;

import java.util.List;

public class TripResponse {
    private String tripPlan;
    private List<List<LatLng>> coordinates;
    private List<String> imagesUrls;

    public TripResponse(String tripPlan, List<List<LatLng>> coordinates, List<String> imagesUrls) {
        this.tripPlan = tripPlan;
        this.coordinates = coordinates;
        this.imagesUrls = imagesUrls;
    }

    public String getTripPlan() {
        return tripPlan;
    }

    public void setTripPlan(String tripPlan) {
        this.tripPlan = tripPlan;
    }

    public List<List<LatLng>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<LatLng>> coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }
}
