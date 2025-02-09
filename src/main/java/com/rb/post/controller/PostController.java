package com.rb.post.controller;

import com.rb.post.dto.PostRequest;
import com.rb.post.dto.ResponseMessage;
import com.rb.post.entity.PostEntity;
import com.rb.post.service.PostService;
import com.rb.post.utils.UtilClass;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/test")
    public String getTest(){
        return "Testing end point........";
    }

    @PostMapping
    public ResponseEntity<PostEntity> post(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        UtilClass.TokenContext.set(request.getHeader("Authorization"));
        return ResponseEntity.ok(postService.postToDB(postRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable("id") Integer id, HttpServletRequest request) {
        UtilClass.TokenContext.set(request.getHeader("Authorization"));
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deletePostById(@PathVariable("id") Integer id, HttpServletRequest request) {
        UtilClass.TokenContext.set(request.getHeader("Authorization"));
        postService.deleteById(id);
        return ResponseEntity.ok(ResponseMessage.builder().message("Post deleted successfully.....").build());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PostEntity>> getPostsOfUser(@PathVariable("userId") Integer userId,HttpServletRequest request) {
        UtilClass.TokenContext.set(request.getHeader("Authorization"));
        return ResponseEntity.ok(postService.getPostsOfUser(userId));
    }
}
