package com.twitter.twitter_app.payload.response;

import com.twitter.twitter_app.models.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserAndPostsResponse {
    private String userName;
    private String userLastName;
    private Set<Role> roles;
    private List<PostDto> posts;
}
