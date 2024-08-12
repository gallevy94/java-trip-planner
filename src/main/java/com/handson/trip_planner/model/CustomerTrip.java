package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.handson.trip_planner.util.Dates;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="customer_trip")
public class CustomerTrip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date createdAt = Dates.nowUTC();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdAt);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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

    public static final class CustomerTripBuilder {
        private Long id;
        private Date createdAt = Dates.nowUTC();
        private Customer customer;
        private String cityName;
        private Integer tripDays;

        private CustomerTripBuilder() {
        }

        public static CustomerTripBuilder aCustomerTrip() {
            return new CustomerTripBuilder();
        }

        public CustomerTripBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerTripBuilder createdAt(Date createdAt) {
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

        public CustomerTrip build() {
            CustomerTrip customerTrip = new CustomerTrip();
            customerTrip.customer = this.customer;
            customerTrip.cityName = this.cityName;
            customerTrip.tripDays = this.tripDays;
            customerTrip.id = this.id;
            customerTrip.createdAt = this.createdAt;
            return customerTrip;
        }
    }
}
