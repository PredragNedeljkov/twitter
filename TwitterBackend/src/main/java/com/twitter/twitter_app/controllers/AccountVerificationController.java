package com.twitter.twitter_app.controllers;

import com.twitter.twitter_app.exceptions.InvalidTokenException;
import com.twitter.twitter_app.models.User;
import com.twitter.twitter_app.repository.UserRepository;
import com.twitter.twitter_app.security.models.SecureToken;
import com.twitter.twitter_app.security.services.SecureTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/register")
public class AccountVerificationController {

    @Value("${front.app.url}")
    private String frontendUrl;
    @Resource
    private UserRepository userRepository;

    @Resource
    private SecureTokenService secureTokenService;

    @GetMapping("/verify")
    public void verifyCustomer(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr, HttpServletResponse response) {
        if (token.isEmpty()) {
            frontendUrl += "verification-failed";

        } else {
            try {
                verifyUser(token);
                frontendUrl += "verification-successful";
            } catch (InvalidTokenException e) {
                frontendUrl += "verification-failed";
            }
        }

        response.setStatus(302);
        response.setHeader("Location", frontendUrl);
    }

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