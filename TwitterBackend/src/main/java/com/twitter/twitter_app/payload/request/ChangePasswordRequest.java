package com.twitter.twitter_app.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordRequest {

    @NotEmpty
    @NotBlank
    private String password;

    @NotEmpty
    @NotBlank
    private String token;
}
