package ust.capstone.Order_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Order_Service.feign.ProductClient;
import ust.capstone.Order_Service.feign.UserClient;
import ust.capstone.Order_Service.feign.VendorClient;
import ust.capstone.Order_Service.model.Order;
import ust.capstone.Order_Service.model.OrderItem;
import ust.capstone.Order_Service.repository.OrderRepository;
import ust.capstone.Order_Service.repository.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private VendorClient vendorClient;

    public Order createOrder(Order order) {
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        // Save the Order first to generate the orderId
        order = orderRepository.save(order);

        double totalAmount = 0.0;
        Set<String> vendorIds = new HashSet<>();

        // Fetch product details, check stock, and update order items
        for (OrderItem item : order.getOrderItems()) {
            Map<String, Object> product = productClient.getProductById(item.getProductId());
            int stockQuantity = (int) product.get("stockQuantity");
            double price = (double) product.get("price");
            String productName = (String) product.get("name");
            String vendorId = (String) product.get("vendorId");

            if (stockQuantity < item.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + productName);
            }

            item.setPrice(price);
            item.setProductName(productName);
            item.setOrderId(order.getId());
            item.setVendorId(vendorId);
            orderItemRepository.save(item);
            totalAmount += price * item.getQuantity();

            productClient.updateProductStock(item.getProductId(), stockQuantity - item.getQuantity());
            vendorIds.add(vendorId);
        }

        order.setTotalAmount(totalAmount);
        order.setVendorIds(new ArrayList<>(vendorIds));
        order = orderRepository.save(order);

        sendEmailNotification(order);

        return order;
    }

    private void sendEmailNotification(Order order) {
        String userEmail = userClient.getUserEmailById(order.getUserId());
        String subject = "Order Confirmation";
        String message = "Your order with ID " + order.getId() + " has been placed successfully.";

        emailSender.sendEmail(userEmail, subject, message);

        for (String vendorId : order.getVendorIds()) {
            String vendorEmail = vendorClient.getVendorContactMailById(vendorId);

            // Gather product details for this vendor
            StringBuilder productDetails = new StringBuilder("Order Details:\n\n");
            for (OrderItem item : order.getOrderItems()) {
                if (item.getVendorId().equals(vendorId)) {
                    productDetails.append("Product Name: ").append(item.getProductName()).append("\n")
                            .append("Quantity: ").append(item.getQuantity()).append("\n")
                            .append("Price: ").append(item.getPrice()).append("\n\n");
                }
            }

            String vendorMessage = message + "\n\n" + productDetails.toString();
            emailSender.sendEmail(vendorEmail, subject, vendorMessage);
        }
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
            orderItem.setId(id);
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
