package com.rb.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb.post.client.UserClient;
import com.rb.post.dto.PostRequest;
import com.rb.post.entity.PostEntity;
import com.rb.post.exception.PostException;
import com.rb.post.repo.PostRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public PostEntity postToDB(PostRequest postRequest) {
        String postedBy = postRequest.getPostedBy();
        final PostEntity post = objectMapper.convertValue(postRequest, PostEntity.class);
        post.setCreatedAt(Instant.now().toString());
        checkUserExistence(postedBy);
        post.setPostedBy(postedBy);
        log.info("Qualified POST : {}",post);
        return postRepository.save(post);
    }

    private void checkUserExistence(String userId) throws PostException{
        try{
            userClient.getUserById(userId);
        }catch (FeignException.BadRequest ex){
            log.info("No user found to post");
            throw new PostException("No user found with id : "+userId);
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

    public List<PostEntity> getPostsOfUser(String userId){
        checkUserExistence(userId);
        List<PostEntity> postsOfUser = postRepository.findByPostedBy(userId);
        if (postsOfUser.isEmpty()) throw new PostException("User : "+userId+" not posted yet");
        else return postsOfUser;
    }

}
