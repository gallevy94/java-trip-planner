//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.handson.trip_planner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.handson.trip_planner.util.Dates;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;

@Entity
@Table(
        name = "customer"
)
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    @Column(
            nullable = false,
            updatable = false
    )
    private @NotNull Date createdAt = Dates.nowUTC();
    private @NotEmpty @Length(
            max = 60
    ) String fullname;
    private String email;

    public Customer() {
    }

    @JsonFormat(
            shape = Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(this.createdAt);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
