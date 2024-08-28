package com.project.vendorService.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.vendorService.model.Vendor;
import com.project.vendorService.service.VendorService;
import com.project.vendorService.repository.VendorRepository;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor registerVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendor(String id, Vendor vendor) {
        Optional<Vendor> existingVendor = vendorRepository.findById(id);
        if (existingVendor.isPresent()) {
            Vendor updatedVendor = existingVendor.get();
            updatedVendor.setName(vendor.getName());
            updatedVendor.setDescription(vendor.getDescription());
            updatedVendor.setContactMail(vendor.getContactMail());
            updatedVendor.setContactPhone(vendor.getContactPhone());
            updatedVendor.setWebsite(vendor.getWebsite());
            updatedVendor.setCity(vendor.getCity());
            return vendorRepository.save(updatedVendor);
        }
        return null; 
    }

    public Vendor getVendorById(String id) {
        return vendorRepository.findById(id).orElse(null);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
}
