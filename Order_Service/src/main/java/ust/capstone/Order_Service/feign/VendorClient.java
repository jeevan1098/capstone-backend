package ust.capstone.Order_Service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "Vendor-Service", url = "http://localhost:5002/api/vendors")
public interface VendorClient {

    @GetMapping("/{vendorId}")
    Map<String, Object> getVendorById(@PathVariable("vendorId") String vendorId);

    @GetMapping("/{vendorId}/contact-mail")
    String getVendorContactMailById(@PathVariable("vendorId") String vendorId);
}
