package com.ecommercebackend.service;


import com.ecommercebackend.api.model.LoginBody;
import com.ecommercebackend.api.model.RegistrationBody;
import com.ecommercebackend.exception.UserAlreadyExistsException;
import com.ecommercebackend.model.LocalUser;
import com.ecommercebackend.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;


// Service for handling user actions.
@Service
public class UserService {

    // When Spring loads UserService its going to create a localUserDAO & inject it into UserService method.
    private LocalUserDAO localUserDAO;
    private EncryptionService encryptionService;

    private JWTService jwtService;

    /**
     * Constructor injected by spring.
     *
     * @param localUserDAO
     * @param encryptionService
     * @param jwtService
     */

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }


     // Attempts to register a user given the information provided.
     // @param registrationBody The registration information.
     // @return The local user that has been written to the database.
     // @throws UserAlreadyExistsException Thrown if there is already a user with the given information.

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getEmail());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
