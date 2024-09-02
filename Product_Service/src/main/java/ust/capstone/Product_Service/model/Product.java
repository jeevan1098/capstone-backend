package ust.capstone.Product_Service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private String categoryId;  // Reference to Category
    private String vendorId;    // Reference to Vendor
    private String vendorName;  // Name of the Vendor
    private int stockQuantity;
    private String imageUrl;
    private List<String> reviewIds = new ArrayList<>(); // Initialize here

    // Constructors
    public Product() {}

    public Product(String id, String name, String description, double price, String categoryId, String vendorId, String vendorName, int stockQuantity, String imageUrl, List<String> reviewIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.reviewIds = reviewIds;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<String> reviewIds) {
        this.reviewIds = reviewIds;
    }
}
