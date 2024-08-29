package ust.capstone.Product_Service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.Product_Service.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByVendorId(String vendorId);
}
