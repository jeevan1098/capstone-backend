package ust.capstone.Order_Service.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.Order_Service.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
    List<OrderItem> findByProductId(String productId);
    List<OrderItem> findByOrderId(String orderId);

    List<OrderItem> findByVendorId(String vendorId);
//    List<OrderItem> findByProductVendorId(String vendorId);
}
