package com.twitter.twitter_app.security.services;

import com.twitter.twitter_app.email.context.AccountVerificationEmailContext;
import com.twitter.twitter_app.email.context.ForgotPasswordEmailContext;
import com.twitter.twitter_app.email.services.EmailService;
import com.twitter.twitter_app.exceptions.InvalidTokenException;
import com.twitter.twitter_app.models.SecureToken;
import com.twitter.twitter_app.models.User;
import com.twitter.twitter_app.repository.SecureTokenRepository;
import com.twitter.twitter_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthHelperService {

    @Resource
    private SecureTokenService secureTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private SecureTokenRepository tokenRepository;

    @Autowired
    private SecureTokenService tokenService;

    @Autowired
    EmailService emailService;


    @Value("${site.base.url.https}")
    private String baseURL;

    @Value("${front.app.url}")
    String frontendBaseUrl;

    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken= tokenService.createSecureToken();
        secureToken.setUser(user);
        tokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean changePassword(String token, String newPassword) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !token.equals(secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        Optional<User> userOpt = userRepository.findById(secureToken.getUser().getId());
        if(userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
        return true;
    }

    public void sendResetPasswordEmail(User user) {
        SecureToken secureToken= tokenService.createSecureToken();
        secureToken.setUser(user);
        tokenRepository.save(secureToken);
        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(frontendBaseUrl, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
