package com.ecommercebackend.model.dao;

import com.ecommercebackend.model.LocalUser;
import com.ecommercebackend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {


    Optional<VerificationToken> findByToken(String token);

    void deleteByLocalUser(LocalUser localUser);


}
