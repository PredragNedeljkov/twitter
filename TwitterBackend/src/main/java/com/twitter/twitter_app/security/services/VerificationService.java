package com.twitter.twitter_app.security.services;

import com.twitter.twitter_app.exceptions.InvalidTokenException;
import com.twitter.twitter_app.models.SecureToken;
import com.twitter.twitter_app.models.User;
import com.twitter.twitter_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class VerificationService {

    @Resource
    private SecureTokenService secureTokenService;

    @Resource
    private UserRepository userRepository;

    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !token.equals(secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        Optional<User> userOpt = userRepository.findById(secureToken.getUser().getId());
        if(userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();
        user.setAccountVerified(true);
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
        return true;
    }

}
