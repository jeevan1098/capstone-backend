package ust.capstone.vendor_service.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.vendor_service.model.Vendor;
import ust.capstone.vendor_service.repository.VendorRepository;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor registerVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendor(String id, Vendor vendor) {
        return vendorRepository.findById(id)
                .map(existingVendor -> {
                    existingVendor.setName(vendor.getName());
                    existingVendor.setDescription(vendor.getDescription());
                    existingVendor.setContactMail(vendor.getContactMail());
                    existingVendor.setContactPhone(vendor.getContactPhone());
                    existingVendor.setWebsite(vendor.getWebsite());
                    existingVendor.setCity(vendor.getCity());
                    return vendorRepository.save(existingVendor);
                })
                .orElse(null);
    }

    public Optional<Vendor> getVendorById(String id) {
        return vendorRepository.findById(id);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
}
