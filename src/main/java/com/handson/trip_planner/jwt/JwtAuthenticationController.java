package com.handson.trip_planner.jwt;

import com.handson.trip_planner.model.Trip;
import com.handson.trip_planner.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private DBUserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager am;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        final String token = tokenProvider.createToken(authentication);
        Long userId = userService.findUserName(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getId();
        return ResponseEntity.ok(new JwtResponse(token, userId));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody JwtRequest userRequest) throws Exception {
        String encodedPass = passwordEncoder.encode(userRequest.getPassword());
        DBUser user = DBUser.UserBuilder.anUser().name(userRequest.getUsername())
                .password(encodedPass).build();
        userService.save(user);
        UserDetails userDetails = new User(userRequest.getUsername(), encodedPass, new ArrayList<>());

        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails), user.getId()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
