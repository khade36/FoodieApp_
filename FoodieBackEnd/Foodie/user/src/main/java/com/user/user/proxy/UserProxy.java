package com.user.user.proxy;




import com.user.user.domain.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="restaurant",url = "localhost:8082")
public interface UserProxy {
    @PostMapping("/api/saveRestoOwner")
    public ResponseEntity<?> saveRestoOwner(@RequestBody User user);
}
