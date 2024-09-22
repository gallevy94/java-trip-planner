package com.handson.trip_planner.jwt;


import com.google.common.base.MoreObjects;
import com.handson.trip_planner.model.Trip;
import org.springframework.data.domain.Persistable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class DBUser implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = -5554304839188669754L;

    protected Long id;

//    public Collection<Trip> getUsersTrips() {
//        return trips;
//    }
//
//    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private Collection<Trip> trips = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Trip> trips;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }


    @Column(nullable = true, length = 50)
    private String googleId;

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Column(nullable = false, length = 60)
    private String name;


    @Column(nullable = false, length = 255)
    private String password;

    public DBUser() {
    }

    @Transient
    public static String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
//        return Hashing.sha256().hashString(password, Charset.defaultCharset()).toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", name)
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password; //DBUser.hashPassword(unencryptedPassword);
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static final class UserBuilder {
        protected Long id;
        private String name;
        private String password;//https://bcrypt-generator.com/ generate password user+email:javainuse,password:$2y$12$JfmXLQVmTZGpeYVgr6AVhejDGynQ739F4pJE1ZjyCPTvKIHTYb2fi

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DBUser build() {
            DBUser user = new DBUser();
            user.setName(name);
            user.setPassword(password);
            user.setId(id);
            return user;
        }
    }
}
