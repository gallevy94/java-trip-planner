package com.handson.trip_planner.security;

import com.handson.trip_planner.config.AppProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        String username = null;
        if (authentication.getPrincipal() instanceof UserPrincipal) {
            username = ((UserPrincipal) authentication.getPrincipal()).getName();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        } else {
            User userPrincipal = (User) authentication.getPrincipal();
            username = userPrincipal.getUsername();

        }


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
            try {
                System.out.println("***********" + appProperties.getAuth().getTokenSecret());
                Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
                return true;
            } catch (SignatureException ex) {
                logger.error("Invalid JWT signature");
            } catch (MalformedJwtException ex) {
                logger.error("Invalid JWT token");
            } catch (ExpiredJwtException ex) {
                logger.error("Expired JWT token");
            } catch (UnsupportedJwtException ex) {
                logger.error("Unsupported JWT token");
            } catch (IllegalArgumentException ex) {
                logger.error("JWT claims string is empty.");
            }
            return false;
        }

}