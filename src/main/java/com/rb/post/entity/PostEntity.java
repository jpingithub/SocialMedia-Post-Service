package com.rb.post.entity;

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
    private String postedBy;
    private String createdAt;

}
