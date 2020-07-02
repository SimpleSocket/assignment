package com.peper.blog.services;

import com.peper.blog.authentication.UserDetailsExtended;
import com.peper.blog.authentication.UserExtended;
import com.peper.blog.entities.PersonEntity;
import com.peper.blog.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserAuth implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetailsExtended loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonEntity personEntity = personRepository.findByEmail(s);

        if(personEntity == null){
            throw new UsernameNotFoundException("Credentials are invalid");
        }

        Integer id = personEntity.getIdPerson();
        String email = personEntity.getEmail();
        String password = personEntity.getPassword();

        UserExtended userExtended = new UserExtended(id, email, password, new ArrayList<>());

        return userExtended;
    }
}

