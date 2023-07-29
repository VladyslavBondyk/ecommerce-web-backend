package com.ecommercebackend.service;


import com.ecommercebackend.api.model.LoginBody;
import com.ecommercebackend.api.model.PasswordResetBody;
import com.ecommercebackend.api.model.RegistrationBody;
import com.ecommercebackend.exception.EmailFailureException;
import com.ecommercebackend.exception.EmailNotFoundException;
import com.ecommercebackend.exception.UserAlreadyExistsException;
import com.ecommercebackend.exception.UserNotVerifiedException;
import com.ecommercebackend.model.LocalUser;
import com.ecommercebackend.model.VerificationToken;
import com.ecommercebackend.model.dao.LocalUserDAO;
import com.ecommercebackend.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


// Service for handling user actions.

@Service
public class UserService {

    // When Spring loads UserService its going to create a localUserDAO & inject it into UserService method.
    private LocalUserDAO localUserDAO;
    private VerificationTokenDAO verificationTokenDAO;
    private EncryptionService encryptionService;

    private JWTService jwtService;
    private EmailService emailService;


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
        // USERNAME IS NOT MANDATORY FOR REGISTRATION. BUT IT WOULD BE OPTIONAL. SO
        // SHOULD I KEEP THOSE CHECKING BY USERNAME OR NOT.
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent())
//            || localUserDAO.findByUsernameIgnoreCase(registrationBody.getEmail()).isPresent())
            {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
//        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createverificationToken(user);
        emailService.sendVeririfcationEmail(verificationToken);
        return localUserDAO.save(user);

    }

    private VerificationToken createverificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setLocalUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }


//        public String loginUser(@org.jetbrains.annotations.NotNull LoginBody loginBody) {
    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {

        Optional<LocalUser> opUser =
                localUserDAO.findByEmailIgnoreCase((loginBody.getEmail()));
        if (opUser.isPresent()) {
        LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().
                            before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createverificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVeririfcationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
         }
        return null;
    }

@Transactional
    public boolean verifyUser(String token) {
        // 1stly try to find token in DB
    Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
    if (opToken.isPresent()) {
        //token taking out
        VerificationToken verificationToken = opToken.get();
        LocalUser user = verificationToken.getLocalUser();
        if (!user.isEmailVerified()) {
            // check if user is already verified
            user.setEmailVerified(true);
            localUserDAO.save(user);
            verificationTokenDAO.deleteByLocalUser(user);
            return true;
        }
    }
    return false;
    }

    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
                if (opUser.isPresent()) {
                 LocalUser user = opUser.get();
                 String token = jwtService.generatePasswordResetJWT(user);
                 emailService.sendPasswordResetEmail(user, token);
                } else {
                    throw new EmailNotFoundException();
                }
    }

    public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            localUserDAO.save(user);
        }
    }

}
