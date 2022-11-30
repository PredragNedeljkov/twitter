package com.twitter.twitter_app.controllers;

import com.twitter.twitter_app.exceptions.InvalidTokenException;
import com.twitter.twitter_app.models.User;
import com.twitter.twitter_app.repository.UserRepository;
import com.twitter.twitter_app.models.SecureToken;
import com.twitter.twitter_app.security.services.SecureTokenService;
import com.twitter.twitter_app.security.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountController {

    @Autowired
    private VerificationService verificationService;

    @Value("${front.app.url}")
    private String frontendUrl;

    @GetMapping("/verify")
    public void verifyCustomer(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr, HttpServletResponse response) {
        if (token.isEmpty()) {
            frontendUrl += "verification-failed";

        } else {
            try {
                verificationService.verifyUser(token);
                frontendUrl += "verification-successful";
            } catch (InvalidTokenException e) {
                frontendUrl += "verification-failed";
            }
        }

        response.setStatus(302);
        response.setHeader("Location", frontendUrl);
    }

}