package com.twitter.twitter_app.payload.response;

import com.twitter.twitter_app.models.UserInReaction;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String userId;

    private String userName;

    private String userLastName;

    private String imagePath;

    private List<UserInReaction> likes;

    private String link;
}

