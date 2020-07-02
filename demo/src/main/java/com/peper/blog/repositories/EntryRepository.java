package com.peper.blog.repositories;

import com.peper.blog.entities.EntryEntity;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<EntryEntity, Integer> {
    boolean existsByFkPersonidPersonAndIdEntry(Integer personId, Integer entryId);
    Iterable<EntryEntity> findAllByFkPersonidPerson(Integer personId);
}
