package ust.capstone.Order_Service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private List<OrderItem> orderItems;
    private List<String> vendorIds;  // List of vendor IDs associated with the order

    public Order() {
        this.orderDate = LocalDateTime.now();  // Set orderDate when the object is instantiated
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<String> getVendorIds() {
        return vendorIds;
    }

    public void setVendorIds(List<String> vendorIds) {
        this.vendorIds = vendorIds;
    }
}
