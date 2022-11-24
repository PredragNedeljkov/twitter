package com.twitter.twitter_app.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class UserAndPostsResponse {
    private String userName;
    private String userLastName;
    private List<PostDto> posts;
}
