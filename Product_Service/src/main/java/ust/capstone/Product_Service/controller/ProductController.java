package ust.capstone.Product_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ust.capstone.Product_Service.model.Product;
import ust.capstone.Product_Service.model.Review;
import ust.capstone.Product_Service.service.ProductService;
import ust.capstone.Product_Service.service.ReviewService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }
    @PutMapping("/{id}/update-stock")
    public ResponseEntity<Product> updateProductStock(@PathVariable String id, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.updateProductStock(id, quantity));
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // Get products by vendor ID
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Product>> getProductsByVendorId(@PathVariable String vendorId) {
        return ResponseEntity.ok(productService.getProductsByVendorId(vendorId));
    }

    // Get reviews by product ID
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(id));
    }
}
