package ust.capstone.Product_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Product_Service.model.Category;
import ust.capstone.Product_Service.model.Product;
import ust.capstone.Product_Service.repository.CategoryRepository;
import ust.capstone.Product_Service.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        updateCategoryWithProduct(savedProduct.getCategoryId(), savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(String id, Product productDetails) {
        Product product = getProductById(id);
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

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByVendorId(String vendorId) {
        return productRepository.findByVendorId(vendorId);
    }

    public List<Product> getProductsByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
