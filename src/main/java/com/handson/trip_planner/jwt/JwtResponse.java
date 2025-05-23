package com.handson.trip_planner.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private Long userId;

//    public JwtResponse(String jwttoken) {
//        this.jwttoken = jwttoken;
//    }

    public String getToken() {
        return this.jwttoken;
    }

    public Long getUserId() {
        return userId;
    }

    public JwtResponse(String jwttoken, Long userId) {
        this.jwttoken = jwttoken;
        this.userId = userId;
    }
}
