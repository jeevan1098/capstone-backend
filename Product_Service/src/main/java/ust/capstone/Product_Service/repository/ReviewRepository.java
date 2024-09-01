package ust.capstone.Product_Service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.Product_Service.model.Review;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(String userId);
}
