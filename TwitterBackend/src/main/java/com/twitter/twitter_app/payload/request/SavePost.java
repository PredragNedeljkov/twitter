package com.twitter.twitter_app.payload.request;

import lombok.Data;

@Data
public class SavePost {
    private String content;
    private String userId;
    private String userName;
    private String userLastName;
    private String image;
}
