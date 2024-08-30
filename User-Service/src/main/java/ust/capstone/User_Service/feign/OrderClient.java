package ust.capstone.User_Service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "Order-Service", url = "http://localhost:5004/api/orders")
public interface OrderClient {

    @GetMapping("/user/{userId}")
    List<Map<String, Object>> getOrdersByUserId(@PathVariable("userId") String userId);
}
