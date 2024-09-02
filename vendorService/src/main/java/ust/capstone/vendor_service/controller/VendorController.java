package ust.capstone.vendor_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ust.capstone.vendor_service.feigen.OrderItemClient;
import ust.capstone.vendor_service.model.Vendor;
import ust.capstone.vendor_service.service.VendorService;
import ust.capstone.vendor_service.exception.VendorNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private OrderItemClient orderItemClient;

    @GetMapping("/{vendorId}/orders")
    public List<?> getOrdersByVendorId(@PathVariable String vendorId) {
        return orderItemClient.getOrderItemsByVendorId(vendorId);
    }

    @PostMapping("/register")
    public ResponseEntity<Vendor> registerVendor(@RequestBody Vendor vendor) {
        Vendor registeredVendor = vendorService.registerVendor(vendor);
        return ResponseEntity.ok(registeredVendor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        try {
            Vendor updatedVendor = vendorService.updateVendor(id, vendor);
            return ResponseEntity.ok(updatedVendor);
        } catch (VendorNotFoundException ex) {
            return ResponseEntity.status(404).body("Vendor not found with id: " + id);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable String id) {
        Optional<Vendor> vendor = vendorService.getVendorById(id);
        if (vendor.isPresent()) {
            return ResponseEntity.ok(vendor.get());
        } else {
            return ResponseEntity.status(404).body("Vendor not found with id: " + id);
        }
    }

    @GetMapping("/{id}/contact-mail")
    public ResponseEntity<?> getVendorContactMailById(@PathVariable String id) {
        try {
            String contactMail = vendorService.getVendorContactMailById(id);
            return ResponseEntity.ok(contactMail);
        } catch (VendorNotFoundException ex) {
            return ResponseEntity.status(404).body("Vendor not found with id: " + id);
        }
    }

    @GetMapping("/contact/{contactMail}")
    public ResponseEntity<?> getVendorByContactMail(@PathVariable String contactMail) {
        try {
            Vendor vendor = vendorService.getVendorByContactMail(contactMail);
            return ResponseEntity.ok(vendor);
        } catch (VendorNotFoundException ex) {
            return ResponseEntity.status(404).body("Vendor not found with contact mail: " + contactMail);
        }
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Vendor vendor) {
        Vendor loggedInVendor = vendorService.login(vendor.getContactMail(), vendor.getPassword());
        if (loggedInVendor != null) {
            return ResponseEntity.ok(loggedInVendor);
        } else {
            return ResponseEntity.status(401).body("Unauthorized: Incorrect contact mail or password");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable String id) {
        try {
            vendorService.deleteVendor(id);
            return ResponseEntity.noContent().build();
        } catch (VendorNotFoundException ex) {
            return ResponseEntity.status(404).body("Vendor not found with id: " + id);
        }
    }

    @DeleteMapping("/contact/{contactMail}")
    public ResponseEntity<String> deleteVendorByContactMail(@PathVariable String contactMail) {
        try {
            vendorService.deleteVendorByContactMail(contactMail);
            return ResponseEntity.noContent().build();
        } catch (VendorNotFoundException ex) {
            return ResponseEntity.status(404).body("Vendor not found with contact mail: " + contactMail);
        }
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<?>> getProductsByVendorId(@PathVariable String id) {
        List<?> products = vendorService.getProductsByVendorId(id);
        return ResponseEntity.ok(products);
    }

}
