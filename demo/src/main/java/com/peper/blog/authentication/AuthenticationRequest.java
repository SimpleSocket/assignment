package com.peper.blog.authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    public AuthenticationRequest() {

    }

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
