package ust.capstone.vendor_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ust.capstone.vendor_service.exception.VendorNotFoundException;
import ust.capstone.vendor_service.feigen.ProductServiceClient;
import ust.capstone.vendor_service.model.Vendor;
import ust.capstone.vendor_service.repository.VendorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductServiceClient productServiceClient;

    public Vendor registerVendor(Vendor vendor) {
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
                    if (vendor.getPassword() != null && !vendor.getPassword().isEmpty()) {
                        existingVendor.setPassword(passwordEncoder.encode(vendor.getPassword()));
                    }
                    return vendorRepository.save(existingVendor);
                })
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id: " + id));
    }

    public Optional<Vendor> getVendorById(String id) {
        return vendorRepository.findById(id);
    }

    public Vendor getVendorByContactMail(String contactMail) {
        return vendorRepository.findByContactMail(contactMail)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with contact mail: " + contactMail));
    }

    public String getVendorContactMailById(String id) {
        Vendor vendor = getVendorById(id)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id: " + id));
        return vendor.getContactMail();
    }

    public Vendor login(String contactMail, String password) {
        Optional<Vendor> optionalVendor = vendorRepository.findByContactMail(contactMail);
        if (optionalVendor.isPresent()) {
            Vendor vendor = optionalVendor.get();
            if (passwordEncoder.matches(password, vendor.getPassword())) {
                return vendor;
            }
        }
        return null;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public void deleteVendor(String id) {
        if (!vendorRepository.existsById(id)) {
            throw new VendorNotFoundException("Vendor not found with id: " + id);
        }
        vendorRepository.deleteById(id);
    }

    public void deleteVendorByContactMail(String contactMail) {
        Vendor vendor = vendorRepository.findByContactMail(contactMail)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with contact mail: " + contactMail));
        vendorRepository.delete(vendor);
    }

    public List<?> getProductsByVendorId(String vendorId) {
        return productServiceClient.getProductsByVendorId(vendorId);
    }
}
