package com.twitter.twitter_app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInReaction {
    private String userId;
    private String name;
    private String lastName;
}