package com.project.vendorService.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.vendorService.model.Vendor;


@Repository
public interface VendorRepository extends MongoRepository<Vendor, String> {
    // Custom query methods can be added here if needed
}
