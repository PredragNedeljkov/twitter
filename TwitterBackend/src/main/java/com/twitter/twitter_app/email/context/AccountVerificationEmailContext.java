package com.twitter.twitter_app.email.context;

import com.twitter.twitter_app.models.User;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;

    @Override
    public <T> void init(T context){
        User customer = (User) context;
        put("firstName", customer.getName());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("customer-service@sample.com");
        setTo(customer.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
