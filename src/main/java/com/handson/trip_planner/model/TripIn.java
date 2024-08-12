package com.handson.trip_planner.model;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static com.handson.trip_planner.model.CustomerTrip.CustomerTripBuilder.aCustomerTrip;

public class TripIn {

    @NotEmpty
    @Length(max = 60)
    private String cityName;

    @Min(1)
    @Max(50)
    private Integer tripDays;

    public CustomerTrip toTrip(Customer customer) {
        return aCustomerTrip().customer(customer).cityName(cityName).tripDays(tripDays).build();
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
}
