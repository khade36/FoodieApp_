package com.user.user.proxy;

import com.user.user.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="order",url = "localhost:8083")
public interface UserOrderProxy {
    @PostMapping("/api/orders/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
