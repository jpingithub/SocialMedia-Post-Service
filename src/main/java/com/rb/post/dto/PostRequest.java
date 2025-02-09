package com.rb.post.dto;

import lombok.Data;

@Data
public class PostRequest {

    private String content;
    private String imageUrl;
    private String postedBy;

}
