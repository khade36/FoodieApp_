package com.user.user.proxy;

import com.user.user.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="favourite",url = "localhost:8086")
public interface UserFavProxy {
    @PostMapping("/api/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
