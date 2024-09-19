package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.LatLng;
//import com.handson.trip_planner.utils.LatLngListConverter;
//import com.handson.trip_planner.utils.CoordinatesConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
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
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

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

    public List<List<LatLng>>getCoordinates() {
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


    public static final class TripBuilder {
        private Long id;
        private @NotNull LocalDateTime createdAt;
        private @NotNull Customer customer;
        private @Length(max = 60) String location;
        private @Length(max = 60) String cityName;
        private @NotEmpty String startDate;
        private @NotEmpty String endDate;
        private String tripPlan;
        private List<List<LatLng>> coordinates;
        private List<String> imagesUrls;

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

        public TripBuilder location(String location) {
            this.location = location;
            return this;
        }

        public TripBuilder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public TripBuilder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public TripBuilder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public TripBuilder tripPlan(String tripPlan) {
            this.tripPlan = tripPlan;
            return this;
        }

        public TripBuilder coordinates(List<List<LatLng>> coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public TripBuilder imagesUrls(List<String> imagesUrls) {
            this.imagesUrls = imagesUrls;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setId(id);
            trip.setCreatedAt(createdAt);
            trip.setCustomer(customer);
            trip.setLocation(location);
            trip.setCityName(cityName);
            trip.setStartDate(startDate);
            trip.setEndDate(endDate);
            trip.setTripPlan(tripPlan);
            trip.setCoordinates(coordinates);
            trip.setImagesUrls(imagesUrls);
            return trip;
        }
    }
}
