package com.twitter.twitter_app.security.services;

import com.twitter.twitter_app.security.models.SecureToken;
import org.apache.tomcat.util.codec.binary.Base64;

import java.time.LocalDateTime;

public interface SecureTokenService {
    SecureToken createSecureToken();

    void saveSecureToken(SecureToken token);

    SecureToken findByToken(String token);

    void removeToken(SecureToken token);

    void removeTokenByToken(String token);
}
