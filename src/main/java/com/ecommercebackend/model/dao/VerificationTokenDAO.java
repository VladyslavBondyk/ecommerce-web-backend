package com.ecommercebackend.model.dao;

import com.ecommercebackend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {

}
