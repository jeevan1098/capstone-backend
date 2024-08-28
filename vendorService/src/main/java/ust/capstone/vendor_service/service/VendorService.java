package ust.capstone.vendor_service.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ust.capstone.vendor_service.model.Vendor;
import ust.capstone.vendor_service.repository.VendorRepository;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Vendor registerVendor(Vendor vendor) {
        // Encrypt the password
        vendor.setPassword(passwordEncoder.encode(vendor.getPassword()));
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
                    existingVendor.setGstno(vendor.getGstno());
                    // Only update the password if it's not null or empty
                    if (vendor.getPassword() != null && !vendor.getPassword().isEmpty()) {
                        existingVendor.setPassword(passwordEncoder.encode(vendor.getPassword()));
                    }
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

    public Vendor login(String contactMail, String password) {
        Vendor vendor = vendorRepository.findByContactMail(contactMail);
        if (vendor != null && passwordEncoder.matches(password, vendor.getPassword())) {
            return vendor;
        }
        return null;
    }

    public void deleteVendor(String id) {
        vendorRepository.deleteById(id);
    }
}
