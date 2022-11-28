package com.twitter.twitter_app.email.services;

import com.twitter.twitter_app.email.context.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(AbstractEmailContext email) throws MessagingException;
}
