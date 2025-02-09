package com.rb.post.client;


import com.rb.post.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE")
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<User> getUserById(@PathVariable("id") Integer userId);

}
