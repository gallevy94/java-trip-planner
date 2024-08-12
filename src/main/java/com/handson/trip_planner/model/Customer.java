package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.handson.trip_planner.util.Dates;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(nullable = false, updatable = false)
    private Date createdAt = Dates.nowUTC();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdAt);
    }

    @NotEmpty
    @Length(max = 60)
    private String fullname;
    private String email;
    public Collection<CustomerTrip> getCustomerTrips() {
        return customerTrips;
    }

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<CustomerTrip> customerTrips = new ArrayList<>();

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final class CustomerBuilder {
        private Long id;
        private @NotNull Date createdAt;
        private @NotEmpty @Length(
                max = 60
        ) String fullname;
        private String email;

        private CustomerBuilder() {
        }

        public static CustomerBuilder aCustomer() {
            return new CustomerBuilder();
        }

        public CustomerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CustomerBuilder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setId(id);
            customer.setCreatedAt(createdAt);
            customer.setFullname(fullname);
            customer.setEmail(email);
            return customer;
        }
    }
}
