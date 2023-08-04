package com.ecommercebackend.service;


import com.ecommercebackend.model.Category;
import com.ecommercebackend.model.PageModel;
import com.ecommercebackend.model.Product;
import com.ecommercebackend.model.dao.ProductDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductDAO productDAO;


    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public PageModel<Product> getAllProducts(Pageable pageable) {
        Page<Product> page = productDAO.findAllBy(pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(page.getContent());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setTotalElement(page.getTotalElements());

        return pageModel;
    }

    public Product getProductById(Long id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Продукт не знайдено"));
    }

    public void updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setLongDescription(updatedProduct.getLongDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        productDAO.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productDAO.delete(product);
    }

    public List<Product> getProductsByCategory (Category category) {
        return productDAO.findByCategory(category);
    }

    public List<Product> getProducts() {
        return productDAO.findAll();
    }
}
