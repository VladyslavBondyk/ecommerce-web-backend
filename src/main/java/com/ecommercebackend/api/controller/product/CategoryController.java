package com.ecommercebackend.api.controller.product;


import com.ecommercebackend.model.Category;
import com.ecommercebackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercebackend.model.Product;
import com.ecommercebackend.model.dao.CategoryDAO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@RestController
@RequestMapping("/categories")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @Operation(summary = "Add category")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return ResponseEntity.ok("Категорію успішно додано ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося додати категорію: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by id")
    public ResponseEntity<String> updateCategoryById(@PathVariable Long id, @RequestBody Category updatedCategory) {
        try {
            categoryService.updateCategory(id, updatedCategory);
            return ResponseEntity.ok("Категорію оновлено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося оновити категорію: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Категорію видалено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Категорію не знайдено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося видалити категорію: " + e.getMessage());
        }
    }

    @GetMapping("/")
    @Operation(summary = "Get all categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }  /// перевірити роботу цього методу

    @GetMapping("/{categoryId}/products")
    @Operation(summary = "Get all products in category")
    public Set<Product> getProductsByCategoryId(@PathVariable Long categoryId) {
        return categoryService.getProductsByCategoryId(categoryId);
    }

}
