package com.handson.trip_planner.model;

import com.handson.trip_planner.model.Customer.CustomerBuilder;
import com.handson.trip_planner.util.Dates;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

public class CustomerIn implements Serializable {
    private @Length(
            max = 60
    ) String fullname;
    private String email;

    public CustomerIn() {
    }

    public Customer toCustomer() {
        return CustomerBuilder.aCustomer().createdAt(Dates.nowUTC()).fullname(this.fullname).email(this.email).build();
    }

    public void updateCustomer(Customer customer) {
        customer.setFullname(this.fullname);
        customer.setEmail(this.email);
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
