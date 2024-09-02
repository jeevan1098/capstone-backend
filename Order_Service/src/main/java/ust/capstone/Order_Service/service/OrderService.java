package ust.capstone.Order_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Order_Service.feign.ProductClient;
import ust.capstone.Order_Service.feign.UserClient;
import ust.capstone.Order_Service.model.Order;
import ust.capstone.Order_Service.model.OrderItem;
import ust.capstone.Order_Service.repository.OrderRepository;
import ust.capstone.Order_Service.repository.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EmailSenderService emailSender;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    public Order createOrder(Order order) {
        // Ensure orderDate is set
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        // Save the Order first to generate the orderId
        order = orderRepository.save(order);

        // Calculate total amount
        double totalAmount = 0.0;

        // Fetch product details, check stock, and update order items
        for (OrderItem item : order.getOrderItems()) {
            Map<String, Object> product = productClient.getProductById(item.getProductId());
            int stockQuantity = (int) product.get("stockQuantity");
            double price = (double) product.get("price");
            String productName = (String) product.get("name");

            if (stockQuantity < item.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + productName);
            }

            item.setPrice(price);
            item.setProductName(productName);
            item.setOrderId(order.getId());  // Set the generated orderId in the OrderItem
            orderItemRepository.save(item);
            totalAmount += price * item.getQuantity();

            // Update product stock
            productClient.updateProductStock(item.getProductId(), stockQuantity - item.getQuantity());
        }

        // Update total amount and save the order again
        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        // Send email notifications
        sendEmailNotification(order);

        return order;
    }


    private void sendEmailNotification(Order order) {
        String userEmail = userClient.getUserEmailById(order.getUserId());  // Fetch user's email using Feign client
        String vendorEmail = "gjeevan0527@gmail.com";  // Placeholder for actual vendor email
        String subject = "Order Confirmation";
        String message = "Your order with ID " + order.getId() + " has been placed successfully.";

        emailSender.sendEmail(userEmail, subject, message);
        emailSender.sendEmail(vendorEmail, subject, message);
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrder(String id, Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(id);
            return orderRepository.save(order);
        } else {
            return null;
        }
    }

    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem updateOrderItem(String id, OrderItem orderItem) {
        if (orderItemRepository.existsById(id)) {
            orderItem.setId(id); // Ensure the ID is set for the update
            return orderItemRepository.save(orderItem);
        } else {
            return null;
        }
    }

    public boolean deleteOrderItem(String id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
