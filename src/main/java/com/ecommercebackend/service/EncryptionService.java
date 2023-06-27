package com.ecommercebackend.service;

// Class responsible for encrypting and verifying encrypted data. Using bcrypt for encrypting data

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    // {ecnryption - is where we r using/group of usage. and variables
    @Value("${encryption.salt.rounds}")    //Spring uses sort rounds
    private int saltRounds;
    private String salt;

    @PostConstruct     // then it causes postconstruct
    public void postConstruct() {
        salt = BCrypt.gensalt(saltRounds);
    }

    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, salt);
    }

    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
