package com.rb.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb.post.client.UserClient;
import com.rb.post.dto.PostRequest;
import com.rb.post.dto.User;
import com.rb.post.entity.PostEntity;
import com.rb.post.exception.PostException;
import com.rb.post.repo.PostRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;
    private final UserClient userClient;

    public PostEntity postToDB(PostRequest postRequest,String userName) {
        final PostEntity post = objectMapper.convertValue(postRequest, PostEntity.class);
        post.setCreatedAt(Instant.now().toString());
        checkUserExistence(userName);
        post.setPostedBy(userName);
        log.info("Qualified POST : {}",post);
        return postRepository.save(post);
    }

    private void checkUserExistence(String userName) throws PostException{
        ResponseEntity<User> userResponseEntity = userClient.searchUser(userName);
        if(userResponseEntity.getStatusCode()== HttpStatus.OK){
            log.info("User found with user name : {}",userName);
        }else{
            log.info("No user found to post");
            throw new PostException("No user found with username : "+userName);
        }
    }

    public PostEntity getPostById(Integer id){
        Optional<PostEntity> byId = postRepository.findById(id);
        if (byId.isPresent()) return byId.get();
        else throw new PostException("No post found with id : "+id);
    }

    public void deleteById(Integer id){
        Optional<PostEntity> byId = postRepository.findById(id);
        if (byId.isPresent()) postRepository.deleteById(id);
        else throw new PostException("No post found with id : "+id);
    }

    public List<PostEntity> getPostsOfUser(String userName){
        checkUserExistence(userName);
        List<PostEntity> postsOfUser = postRepository.findByPostedBy(userName);
        if (postsOfUser.isEmpty()) throw new PostException("User : "+userName+" not posted yet");
        else return postsOfUser;
    }

}
