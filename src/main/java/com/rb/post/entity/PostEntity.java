package com.rb.post.entity;

import com.rb.post.dto.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @Column(length = 100000)
    private String imageUrl;
    private Integer postedBy;
    private String createdAt;

}
