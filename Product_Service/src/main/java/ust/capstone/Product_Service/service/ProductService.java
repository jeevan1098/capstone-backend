package ust.capstone.Product_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Product_Service.feign.VendorClient;
import ust.capstone.Product_Service.model.Category;
import ust.capstone.Product_Service.model.Product;
import ust.capstone.Product_Service.repository.CategoryRepository;
import ust.capstone.Product_Service.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorClient vendorClient;

    // Create a new product
    public Product createProduct(Product product) {
        // Fetch vendor details
        Map<String, Object> vendor = vendorClient.getVendorById(product.getVendorId());
        String vendorName = (String) vendor.get("name");

        // Set vendor name in product
        product.setVendorName(vendorName);

        // Save product and update category
        Product savedProduct = productRepository.save(product);
        updateCategoryWithProduct(savedProduct.getCategoryId(), savedProduct.getId());
        return savedProduct;
    }

    // Update product stock quantity
    public Product updateProductStock(String id, int quantity) {
        Product product = getProductById(id);
        if (product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Insufficient stock");
        }
    }

    // Update an existing product
    public Product updateProduct(String id, Product productDetails) {
        Product product = getProductById(id);

        // Fetch vendor details if vendorId is updated
        if (!product.getVendorId().equals(productDetails.getVendorId())) {
            Map<String, Object> vendor = vendorClient.getVendorById(productDetails.getVendorId());
            String vendorName = (String) vendor.get("name");
            product.setVendorName(vendorName);
        }

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategoryId(productDetails.getCategoryId());

        Product updatedProduct = productRepository.save(product);
        updateCategoryWithProduct(updatedProduct.getCategoryId(), updatedProduct.getId());
        return updatedProduct;
    }

    // Update the category with the product
    private void updateCategoryWithProduct(String categoryId, String productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.getProductIds() == null) {
            category.setProductIds(new ArrayList<>());
        }

        if (!category.getProductIds().contains(productId)) {
            category.getProductIds().add(productId);
            categoryRepository.save(category);
        }
    }

    // Get product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Delete a product
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    // Get products by vendor ID
    public List<Product> getProductsByVendorId(String vendorId) {
        return productRepository.findByVendorId(vendorId);
    }

    // Get products by category ID
    public List<Product> getProductsByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
