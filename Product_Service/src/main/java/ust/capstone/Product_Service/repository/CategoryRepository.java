package ust.capstone.Product_Service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.Product_Service.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
    // Additional methods if required in the future
}
