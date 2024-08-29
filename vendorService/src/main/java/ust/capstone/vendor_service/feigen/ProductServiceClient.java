package ust.capstone.vendor_service.feigen;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:5000/api/products")
public interface ProductServiceClient {

    @GetMapping("/vendor/{vendorId}")
    List<?> getProductsByVendorId(@PathVariable("vendorId") String vendorId);
}
