package com.peper.blog.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsExtended extends UserDetails {
    Integer getId();
}
