package com.twitter.twitter_app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Post {

    @Id
    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String userId;

    private String userName;

    private String userLastName;

    private List<UserInReaction> likes;

    private String image;
}
