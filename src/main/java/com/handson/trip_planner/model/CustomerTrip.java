package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.handson.trip_planner.util.Dates;
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
public class CustomerTrip implements Serializable {
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

    public static final class CustomerTripBuilder {
        private Long id;
        private LocalDateTime createdAt;
        private Customer customer;
        private String cityName;
        private Integer tripDays;
        private String tripPlan;

        private CustomerTripBuilder() {
        }

        public static CustomerTripBuilder aCustomerTrip() {
            return new CustomerTripBuilder();
        }

        public CustomerTripBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerTripBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CustomerTripBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public CustomerTripBuilder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public CustomerTripBuilder tripDays(Integer tripDays) {
            this.tripDays = tripDays;
            return this;
        }

        public CustomerTripBuilder tripPlan(String tripPlan) {
            this.tripPlan = tripPlan;
            return this;
        }

        public CustomerTrip build() {
            CustomerTrip customerTrip = new CustomerTrip();
            customerTrip.setId(id);
            customerTrip.setCreatedAt(createdAt);
            customerTrip.setCustomer(customer);
            customerTrip.setCityName(cityName);
            customerTrip.setTripDays(tripDays);
            customerTrip.setTripPlan(tripPlan);
            return customerTrip;
        }
    }
}
