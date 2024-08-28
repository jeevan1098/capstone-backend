package ust.capstone.vendor_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ust.capstone.vendor_service.model.Vendor;
import ust.capstone.vendor_service.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/register")
    public ResponseEntity<Vendor> registerVendor(@RequestBody Vendor vendor) {
        Vendor registeredVendor = vendorService.registerVendor(vendor);
        return ResponseEntity.ok(registeredVendor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendor);
        if (updatedVendor != null) {
            return ResponseEntity.ok(updatedVendor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable String id) {
        return vendorService.getVendorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @PostMapping("/login")
    public ResponseEntity<Vendor> login(@RequestBody Vendor vendor) {
        Vendor loggedInVendor = vendorService.login(vendor.getContactMail(), vendor.getPassword());
        if (loggedInVendor != null) {
            return ResponseEntity.ok(loggedInVendor);
        } else {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable String id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
}
