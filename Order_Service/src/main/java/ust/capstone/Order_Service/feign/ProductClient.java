package ust.capstone.Order_Service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "Product-Service", url = "http://localhost:5003/api/products")
public interface ProductClient {

    @GetMapping("/{productId}")
    Map<String, Object> getProductById(@PathVariable("productId") String productId);

    @PutMapping("/{productId}/update-stock")
    void updateProductStock(@PathVariable("productId") String productId, @RequestParam("quantity") int quantity);
}
