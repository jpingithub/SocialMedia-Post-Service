package com.rb.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb.post.client.UserClient;
import com.rb.post.dto.PostRequest;
import com.rb.post.entity.PostEntity;
import com.rb.post.exception.PostException;
import com.rb.post.repo.PostRepository;
import com.rb.post.utils.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;
    private final UserClient userClient;

    public PostEntity postToDB(PostRequest postRequest) {
        Integer postedBy = postRequest.getPostedBy();
        final PostEntity post = objectMapper.convertValue(postRequest, PostEntity.class);
        post.setCreatedAt(Instant.now().toString());
        if(userClient.getUserById(postedBy).getStatusCode() == HttpStatus.OK){
            UtilClass.TokenContext.remove();
            post.setPostedBy(postedBy);
            return postRepository.save(post);
        }else throw new PostException("No post owner found ");
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

    public List<PostEntity> getPostsOfUser(Integer userId){
        if(userClient.getUserById(userId).getStatusCode()== HttpStatus.OK){
            UtilClass.TokenContext.remove();
            List<PostEntity> postsOfUser = postRepository.findByPostedBy(userId);
            if (postsOfUser.isEmpty()) throw new PostException("No posts found for the user : "+userId);
            else return postsOfUser;
        }else throw new PostException("No user found with id : "+userId);
    }

}
