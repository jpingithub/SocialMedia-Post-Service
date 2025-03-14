package com.rb.post.controller;

import com.rb.post.dto.PostRequest;
import com.rb.post.entity.PostEntity;
import com.rb.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostEntity> post(@RequestBody PostRequest postRequest, @RequestHeader("${customized-header-for-token}") String username) {
        return ResponseEntity.ok(postService.postToDB(postRequest,username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") Integer id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<PostEntity>> getPostsOfUser(@RequestHeader("${customized-header-for-token}") String username) {
        return ResponseEntity.ok(postService.getPostsOfUser(username));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<PostEntity>> getPostsOfOtherUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(postService.getPostsOfUser(username));
    }
}
