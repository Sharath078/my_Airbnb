package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.duration}")
    private long expiryTime;

    private Algorithm algorithm;
    private static final String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(PropertyUser propertyUser) {
        return JWT.create()
                .withClaim(USER_NAME, propertyUser.getUsername())
                .withExpiresAt(new Date(currentTimeMillis() + expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token) throws TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
