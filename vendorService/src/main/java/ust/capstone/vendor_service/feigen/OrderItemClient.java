package ust.capstone.vendor_service.feigen;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "Order-Service", url = "http://localhost:5004/api/order-items")
public interface OrderItemClient {

    @GetMapping("/vendor/{vendorId}")
    List<?> getOrderItemsByVendorId(@PathVariable("vendorId") String vendorId);
}
