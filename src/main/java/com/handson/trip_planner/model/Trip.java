package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="customer_trip")
public class Trip implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @NotEmpty
    @Length(max = 60)
    private String cityName;

    @Min(1)
    @Max(50)
    private Integer tripDays;

    @Column(name = "trip_plan", columnDefinition = "jsonb")
    private String tripPlan;

    @Column(name = "routes", columnDefinition = "jsonb")
    private String routes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getTripDays() {
        return tripDays;
    }

    public void setTripDays(Integer tripDays) {
        this.tripDays = tripDays;
    }

    public String getTripPlan() {
        return tripPlan;
    }

    public void setTripPlan(String tripPlan) {
        this.tripPlan = tripPlan;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }


    public static final class TripBuilder {
        private Long id;
        private @NotNull LocalDateTime createdAt;
        private @NotNull Customer customer;
        private @NotEmpty @Length(max = 60) String cityName;
        private @Min(1) @Max(50) Integer tripDays;
        private String tripPlan;
        private String routes;

        private TripBuilder() {
        }

        public static TripBuilder aTrip() {
            return new TripBuilder();
        }

        public TripBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TripBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TripBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public TripBuilder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public TripBuilder tripDays(Integer tripDays) {
            this.tripDays = tripDays;
            return this;
        }

        public TripBuilder tripPlan(String tripPlan) {
            this.tripPlan = tripPlan;
            return this;
        }

        public TripBuilder routes(String routes) {
            this.routes = routes;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setId(id);
            trip.setCreatedAt(createdAt);
            trip.setCustomer(customer);
            trip.setCityName(cityName);
            trip.setTripDays(tripDays);
            trip.setTripPlan(tripPlan);
            trip.setRoutes(routes);
            return trip;
        }
    }
}
