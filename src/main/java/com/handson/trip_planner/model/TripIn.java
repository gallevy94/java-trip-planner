package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static com.handson.trip_planner.model.CustomerTrip.CustomerTripBuilder.aCustomerTrip;

public class TripIn {

    @NotEmpty
    @Length(max = 60)
    private String cityName;

    @Min(1)
    @Max(20)
    private Integer tripDays;

    @Column(name = "trip_plan", columnDefinition = "jsonb")
    private String tripPlan;


    public CustomerTrip toTrip(Customer customer) {
        return aCustomerTrip().customer(customer).cityName(cityName).tripDays(tripDays).tripPlan(tripPlan).build();
    }

    public void updateCustomerTrip(CustomerTrip customerTrip) {
        customerTrip.setCityName(cityName);
        customerTrip.setTripDays(tripDays);
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

        public TripIn build() {
            TripIn tripIn = new TripIn();
            tripIn.setTripPlan(tripPlan);
            tripIn.cityName = this.cityName;
            tripIn.tripDays = this.tripDays;
            return tripIn;
        }
    }
}
