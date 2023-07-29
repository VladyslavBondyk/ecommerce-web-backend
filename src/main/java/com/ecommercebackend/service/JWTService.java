package com.ecommercebackend.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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
    private static final String VERIFICATION_EMAIL_KEY = "VERIFICATION_EMAIL";
    private static final String RESET_PASSWORD_EMAIL_KEY = "RESET_PASSWORD_EMAIL";


//     Post construction method.

    @PostConstruct
    public void postConstruct() {
    algorithm = Algorithm.HMAC256(algorithmKey);
    }    // here we can use SSH key, but for now it d be HM AC implementation algorithm

    public String generateJWT(LocalUser user) {
        return JWT.create()
        .withClaim(VERIFICATION_EMAIL_KEY, user.getEmail())    //claim is payload
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000L * expiryInSecond)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String generateVerificationJWT(LocalUser user) {
        return JWT.create()
            .withClaim(VERIFICATION_EMAIL_KEY, user.getEmail())    //claim is payload
            .withExpiresAt(new Date(System.currentTimeMillis() + (1000L * expiryInSecond)))
            .withIssuer(issuer)
            .sign(algorithm);
    }

    public String generatePasswordResetJWT(LocalUser user) {
        return JWT.create()
                .withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())    //claim is payload
                //1s + 1min + 30min limit for a user to reset a password
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

        public String getResetPasswordEmail(String token) {
            DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
            return jwt.getClaim(RESET_PASSWORD_EMAIL_KEY).asString();
        }

    public String getEmail(String token) {
        // JWT library, I ve this algoritm, pls build be verification class which knows HT verify with such algtm and then verify this token.
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(VERIFICATION_EMAIL_KEY).asString();   //check if the token even real
    }

}
