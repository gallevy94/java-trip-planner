package com.handson.trip_planner.model;


import com.handson.trip_planner.utils.Dates;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

import static com.handson.trip_planner.model.Customer.CustomerBuilder.aCustomer;

public class CustomerIn implements Serializable {
    @Length(max = 60)
    private String fullname;
    private String email;


    public Customer toCustomer() {
        return aCustomer().createdAt(Dates.nowUTC()).fullname(fullname).email(email)
                .build();
    }

    public void updateCustomer(Customer customer) {
        customer.setFullname(fullname);
        customer.setEmail(email);
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

}
