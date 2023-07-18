package com.ecommercebackend.service;


import com.ecommercebackend.api.model.LoginBody;
import com.ecommercebackend.api.model.RegistrationBody;
import com.ecommercebackend.exception.EmailFailureException;
import com.ecommercebackend.exception.UserAlreadyExistsException;
import com.ecommercebackend.model.LocalUser;
import com.ecommercebackend.model.VerificationToken;
import com.ecommercebackend.model.dao.LocalUserDAO;
import com.ecommercebackend.model.dao.VerificationTokenDAO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Service for handling user actions.
 */
@Service
public class UserService {

    // When Spring loads UserService its going to create a localUserDAO & inject it into UserService method.
    private LocalUserDAO localUserDAO;
    private VerificationTokenDAO verificationTokenDAO;
    private EncryptionService encryptionService;

    private JWTService jwtService;
    private EmailService emailService

    /**
     * Constructor injected by spring.
     *
     * @param localUserDAO
     * @param verificationTokenDAO
     * @param encryptionService
     * @param jwtService
     * @param emailService
     */

    public UserService(LocalUserDAO localUserDAO, VerificationTokenDAO verificationTokenDAO,
                       EncryptionService encryptionService, JWTService jwtService,
                       EmailService emailService) {
        this.localUserDAO = localUserDAO;
        this.verificationTokenDAO = verificationTokenDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
        // In order to avoid error msgs in server -≥
        // below if statement searching for email OR username duplicates.
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || localUserDAO.findByUsernameIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
//        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createerificationToken(user);
        emailService.sendVeririfcationEmail(verificationToken);
        return localUserDAO.save(user);

    }

    private VerificationToken createerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setLocalUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }


//        public String loginUser(@org.jetbrains.annotations.NotNull LoginBody loginBody) {
    public String loginUser(LoginBody loginBody) {

        Optional<LocalUser> opUser =
                localUserDAO.findByEmailIgnoreCase((loginBody.getEmail()));
        if (opUser.isPresent()) {
        LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
         }
        return null;
    }
}
