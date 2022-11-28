package com.twitter.twitter_app.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ForgottenPasswordRequest {
    @NotBlank
    @NotEmpty
    @Email
    String email;

}
