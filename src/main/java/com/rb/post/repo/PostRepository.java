package com.rb.post.repo;

import com.rb.post.dto.User;
import com.rb.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {
    List<PostEntity> findByPostedBy(Integer userId);
}
