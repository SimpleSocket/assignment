package com.peper.blog.services;

import com.peper.blog.entities.PersonEntity;
import com.peper.blog.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PersonRepository personRepository;

    public boolean createPerson(PersonEntity personEntity){
        if(personRepository.existsByEmail(personEntity.getEmail())){
            return false;
        }

        PersonEntity savedPerson = personRepository.save(personEntity);
        return savedPerson != null;
    }


}
