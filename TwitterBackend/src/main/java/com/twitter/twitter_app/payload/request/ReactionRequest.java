package com.twitter.twitter_app.payload.request;

import lombok.Data;

@Data
public class ReactionRequest {
    private String postId;
    private String userId;
    private String userName;
    private String userLastName;
}

