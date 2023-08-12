package com.ecommercebackend.api.controller.product;


import com.ecommercebackend.model.Category;
import com.ecommercebackend.model.PageModel;
import com.ecommercebackend.model.Product;
import com.ecommercebackend.service.CategoryService;
import com.ecommercebackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductController(ProductService productService, CategoryService categoryService) {

        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @Operation(summary = "Add product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {

        try {
            Long categoryId = product.getCategory().getId();
            Category category = categoryService.getCategoryById(categoryId);

            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Category with ID " + categoryId + " not found");
            }

            product.setCategory(category);
            productService.addProduct(product);
            return ResponseEntity.ok("Product is added");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add product: " + ex.getMessage());
        }


//        try {
//            Category category = categoryService.getCategoryById(product.getCategory().getId());
//            product.setCategory(category);
//            productService.addProduct(product);
//            return ResponseEntity.ok("Product is added");
//        } catch (NoSuchElementException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to add product: " + ex.getMessage());
//        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public Product getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        product.getCategory().getName();
        return product;
    } /// не виводяться імя продукту та категорії id та імя

    @GetMapping("/")
    @Operation(summary = "Get all products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ResponseEntity<String> updateProductById(
            @PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok("Продукт оновлено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося оновити продукт: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Продукт успішно видалено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Продукт не знайдено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося видалити продукт: " + e.getMessage());
        }
    }



    @GetMapping
    public List<Product> getProducts(
            @RequestParam(name = "popular", required = false) Boolean popular,
            @RequestParam(name = "seasonNovelties", required = false) Boolean seasonNovelties)
    {
        if (Boolean.TRUE.equals(popular) && Boolean.TRUE.equals(seasonNovelties)) {
            return productService.getPopularAndSeasonNovelties();
        } else if (Boolean.TRUE.equals(popular)) {
            return productService.getPopularProducts();
        } else if (Boolean.TRUE.equals(seasonNovelties)) {
            return productService.getSeasonNovelties();
        } else {
            return productService.getProducts();
        }
    }

}
