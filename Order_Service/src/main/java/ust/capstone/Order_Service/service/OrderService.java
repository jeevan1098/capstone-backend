package ust.capstone.Order_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Order_Service.model.Order;
import ust.capstone.Order_Service.model.OrderItem;
import ust.capstone.Order_Service.repository.OrderRepository;
import ust.capstone.Order_Service.repository.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order createOrder(Order order) {
        // Ensure orderDate is set
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        // Save the Order to generate the orderId
        order = orderRepository.save(order);

        // Calculate total amount
        double totalAmount = 0.0;

        // Set the orderId for each OrderItem and save them
        for (OrderItem item : order.getOrderItems()) {
            item.setOrderId(order.getId());  // Set the orderId
            orderItemRepository.save(item);  // Save each item
            totalAmount += item.getPrice() * item.getQuantity();  // Calculate total
        }

        // Update the total amount and save the order again
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
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
