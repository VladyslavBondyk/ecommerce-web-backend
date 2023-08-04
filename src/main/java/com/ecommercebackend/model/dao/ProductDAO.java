package com.ecommercebackend.model.dao;

import com.ecommercebackend.model.Category;
import com.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
    Page<Product> findAllBy(Pageable pageable);

    List<Product> findByCategory(Category category);
}
