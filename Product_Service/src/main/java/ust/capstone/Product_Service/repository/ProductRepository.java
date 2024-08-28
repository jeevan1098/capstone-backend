package ust.capstone.Product_Service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.Product_Service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Add methods to retrieve products by category, etc.
}

