package com.peper.blog.utilities;

import com.peper.blog.authentication.UserDetailsExtended;
import com.peper.blog.authentication.UserExtended;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public UserDetailsExtended getCurrentPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsExtended userDetailsExtended = (UserDetailsExtended) authentication.getPrincipal();
        return userDetailsExtended;
    }

    public Integer getCurrentPrincipalId(){
        UserDetailsExtended userDetailsExtended = getCurrentPrincipal();
        return userDetailsExtended.getId();
    }


}
