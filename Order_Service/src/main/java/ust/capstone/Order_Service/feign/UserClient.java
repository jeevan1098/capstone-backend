package ust.capstone.Order_Service.feign;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "User-Service", url = "http://localhost:5001/api/users")
public interface UserClient {

    @GetMapping("/{userId}/email")
    String getUserEmailById(@PathVariable("userId") String userId);
}
