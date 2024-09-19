package com.handson.trip_planner.model;

import com.google.maps.model.LatLng;
//import com.handson.trip_planner.utils.LatLngListConverter;
//import com.handson.trip_planner.utils.CoordinatesConverter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.List;

import static com.handson.trip_planner.model.Trip.TripBuilder.aTrip;

public class TripIn {

    @Length(max = 60)
    private String location;
    @Length(max = 60)
    private String cityName;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String endDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String tripPlan;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<List<LatLng>> coordinates;


    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> imagesUrls;

    public Trip toTrip(Customer customer) {
        return aTrip()
                .customer(customer)
                .location(location)
                .cityName(cityName)
                .startDate(startDate)
                .endDate(endDate)
                .tripPlan(tripPlan)
                .coordinates(coordinates)
                .imagesUrls(imagesUrls)
                .build();
    }

    public void updateCustomerTrip(Trip trip) {}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public static final class TripInBuilder {
        private @Length(max = 60) String location;
        private @Length(max = 60) String cityName;
        private @NotEmpty String startDate;
        private @NotEmpty String endDate;
        private String tripPlan;
        private List<List<LatLng>> coordinates;
        private List<String> imagesUrls;

        private TripInBuilder() {
        }

        public static TripInBuilder aTripIn() {
            return new TripInBuilder();
        }

        public TripInBuilder location(String location) {
            this.location = location;
            return this;
        }

        public TripInBuilder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public TripInBuilder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public TripInBuilder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public TripInBuilder tripPlan(String tripPlan) {
            this.tripPlan = tripPlan;
            return this;
        }

        public TripInBuilder coordinates(List<List<LatLng>> coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public TripInBuilder imagesUrls(List<String> imagesUrls) {
            this.imagesUrls = imagesUrls;
            return this;
        }

        public TripIn build() {
            TripIn tripIn = new TripIn();
            tripIn.setLocation(location);
            tripIn.setCityName(cityName);
            tripIn.setStartDate(startDate);
            tripIn.setEndDate(endDate);
            tripIn.setTripPlan(tripPlan);
            tripIn.setCoordinates(coordinates);
            tripIn.setImagesUrls(imagesUrls);
            return tripIn;
        }
    }
}
