package com.twitter.twitter_app.security.models;

import com.twitter.twitter_app.models.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Document(collection = "secureTokens")
public class SecureToken{
    @Id
    private String id;

    private String token;

    private Timestamp timeStamp;

    private LocalDateTime expireAt;

    private User user;

    @Transient
    private boolean isExpired;

    public boolean isExpired() {
        return getExpireAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
    }
    //getter an setter
}