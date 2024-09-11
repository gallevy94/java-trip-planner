package com.handson.trip_planner.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static com.handson.trip_planner.model.Trip.TripBuilder.aTrip;

public class TripIn {

    @NotEmpty
    @Length(max = 60)
    private String cityName;

    @Min(1)
    @Max(20)
    private Integer tripDays;

    @Column(name = "trip_plan", columnDefinition = "jsonb")
    private String tripPlan;

    @Column(name = "routes", columnDefinition = "jsonb")
    private String routes;


    public Trip toTrip(Customer customer) {
        return aTrip()
                .customer(customer)
                .cityName(cityName)
                .tripDays(tripDays)
                .tripPlan(tripPlan)
                .build();
    }

    public void updateCustomerTrip(Trip trip) {
        trip.setCityName(cityName);
        trip.setTripDays(tripDays);
    }

    public String getCityName() {
        return cityName;
    }

    public Integer getTripDays() {
        return tripDays;
    }

    public String getTripPlan() {
        return tripPlan;
    }

    public void setTripPlan(String tripPlan) {
        this.tripPlan = tripPlan;
    }

    public static final class TripInBuilder {
        private @NotEmpty @Length(max = 60) String cityName;
        private @Min(1) @Max(20) Integer tripDays;
        private String tripPlan;
        private String routes;

        private TripInBuilder() {
        }

        public static TripInBuilder aTripIn() {
            return new TripInBuilder();
        }

        public TripInBuilder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public TripInBuilder tripDays(Integer tripDays) {
            this.tripDays = tripDays;
            return this;
        }

        public TripInBuilder tripPlan(String tripPlan) {
            this.tripPlan = tripPlan;
            return this;
        }

        public TripInBuilder routes(String routes) {
            this.routes = routes;
            return this;
        }

        public TripIn build() {
            TripIn tripIn = new TripIn();
            tripIn.setTripPlan(tripPlan);
            tripIn.tripDays = this.tripDays;
            tripIn.routes = this.routes;
            tripIn.cityName = this.cityName;
            return tripIn;
        }
    }
}
