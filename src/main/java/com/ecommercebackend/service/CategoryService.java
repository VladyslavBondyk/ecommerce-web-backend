package com.ecommercebackend.service;

import com.ecommercebackend.model.Category;
import com.ecommercebackend.model.Product;
import com.ecommercebackend.model.dao.CategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public void addCategory(Category category) {
        categoryDAO.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not Found"));
    }

    public void updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        //    existingCategory.setProducts(updatedCategory.getProducts()); перевірити потім це
        categoryDAO.save(existingCategory);
    }


    public Set<Product> getProductsByCategoryId(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return category.getProducts();
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryDAO.delete(category);
    }


}
