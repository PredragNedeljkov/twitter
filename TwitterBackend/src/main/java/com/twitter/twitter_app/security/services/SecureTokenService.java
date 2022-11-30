package com.twitter.twitter_app.security.services;

import com.twitter.twitter_app.models.SecureToken;

public interface SecureTokenService {
    SecureToken createSecureToken();

    void saveSecureToken(SecureToken token);

    SecureToken findByToken(String token);

    void removeToken(SecureToken token);

    void removeTokenByToken(String token);
}
