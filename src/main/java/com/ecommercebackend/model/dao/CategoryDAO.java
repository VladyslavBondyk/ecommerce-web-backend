package com.ecommercebackend.model.dao;

import com.ecommercebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryDAO extends ListCrudRepository<Category, Long> {
}
