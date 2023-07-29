package com.ecommercebackend.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecommercebackend.model.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.util.Date;


//  Service for handling JWTs for user authentication.
//  Responsible for handling the encoded strings which we use ti auth users


@Service
public class JWTService {      //JSON Web Token

//    The secret key to encrypt the JWTs with.
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
//    The issuer the JWT is signed with.
    @Value("${jwt.issuer}")
    private String issuer;
//    How many seconds from generation should the JWT expire?
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSecond;
//     The algorithm generated post construction.
    private Algorithm algorithm;
//     The JWT claim key for the username
    private static final String EMAIL_KEY = "EMAIL";


//     Post construction method.

    @PostConstruct
    public void postConstruct() {
    algorithm = Algorithm.HMAC256(algorithmKey);
    }    // here we can use SSH key, but for now it d be HM AC implementation algorithm

    public String generateJWT(LocalUser user) {
        return JWT.create()
        .withClaim(EMAIL_KEY, user.getEmail())    //claim is payload
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSecond)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String generateVerificationJWT(LocalUser user) {
        return JWT.create()
            .withClaim(EMAIL_KEY, user.getEmail())    //claim is payload
            .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSecond)))
            .withIssuer(issuer)
            .sign(algorithm);
    }

    public String getEmail(String token) {
        return JWT.decode(token).getClaim(EMAIL_KEY).asString();   //check if the token even real
    }

}
