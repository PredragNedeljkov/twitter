package com.twitter.twitter_app.repository;

import com.twitter.twitter_app.models.Role;
import com.twitter.twitter_app.security.models.SecureToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecureTokenRepository extends MongoRepository<SecureToken, String> {
    SecureToken findByToken(String token);

    void removeByToken(String token);
}
