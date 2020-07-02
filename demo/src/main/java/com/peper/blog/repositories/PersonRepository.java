package com.peper.blog.repositories;

import com.peper.blog.entities.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Integer> {
    PersonEntity findByEmail(String email);
}
