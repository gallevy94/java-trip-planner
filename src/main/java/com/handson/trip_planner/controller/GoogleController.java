package com.handson.trip_planner.controller;

import com.handson.trip_planner.jwt.DBUser;
import com.handson.trip_planner.jwt.DBUserService;
import com.handson.trip_planner.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
public class GoogleController {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    DBUserService userService;

    @GetMapping("/google")
    public ModelAndView index(HttpServletRequest httpRequest, Model model, @RequestParam(required = false) String token) throws Exception {
        if (token != null) {
            tokenProvider.validateToken(token);
            String username = tokenProvider.getUserNameFromToken(token);
            Optional<DBUser> userOpt = userService.findUserById(Long.parseLong(username));
            if (userOpt.isPresent()) {
                Authentication authentication=        new UsernamePasswordAuthenticationToken(
                        userOpt.get().getName(),
                        "1111"
                );

                String newToken = tokenProvider.createToken(authentication);
                return new ModelAndView("redirect:https://web.gal-trip-planner.com/dologin?token=" + newToken + "&username=" + userOpt.get().getName() + "&id=" + userOpt.get().getId());
            }
        }
        throw new Exception("token invalid or user not found");
    }


    @GetMapping("/googlelocal")
    public ModelAndView index1(HttpServletRequest httpRequest, Model model, @RequestParam(required = false) String token) throws Exception {
        if (token != null) {
            tokenProvider.validateToken(token);
            String username = tokenProvider.getUserNameFromToken(token);
            System.out.println("************** user name local google" + username);
            Optional<DBUser> userOpt = userService.findUserById(Long.parseLong(username));
            if (userOpt.isPresent()) {
                Authentication authentication=        new UsernamePasswordAuthenticationToken(
                        userOpt.get().getName(),
                        "1111"
                );

                String newToken = tokenProvider.createToken(authentication);
                return new ModelAndView("redirect:http://localhost:4200/dologin?token=" + newToken + "&username=" + userOpt.get().getName());
            }


        }
        throw new Exception("token invalid or user not found");
    }

}

