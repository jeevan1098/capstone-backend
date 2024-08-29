package ust.capstone.Order_Service.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.Order_Service.model.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}

