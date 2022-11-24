package com.twitter.twitter_app.payload.response;

import com.twitter.twitter_app.models.UserInReaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponse {
    private List<UserInReaction> likes;
}
