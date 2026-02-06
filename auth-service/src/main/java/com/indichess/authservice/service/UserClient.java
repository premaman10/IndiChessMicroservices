package com.indichess.authservice.service;

import com.indichess.authservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/{username}")
    User getUserByUsername(@PathVariable("username") String username);

    @PostMapping("/api/users")
    User saveUser(@RequestBody User user);
}
