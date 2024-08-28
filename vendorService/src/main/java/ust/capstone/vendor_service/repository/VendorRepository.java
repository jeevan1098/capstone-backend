package ust.capstone.vendor_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ust.capstone.vendor_service.model.Vendor;

@Repository
public interface VendorRepository extends MongoRepository<Vendor, String> {
    // Custom query methods can be added here if needed
}
