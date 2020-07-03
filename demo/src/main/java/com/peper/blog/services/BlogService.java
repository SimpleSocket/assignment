package com.peper.blog.services;

import com.peper.blog.entities.EntryEntity;
import com.peper.blog.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class BlogService {

    @Autowired
    private EntryRepository entryRepository;

    private boolean entryExistsForGivenPersonId(int personId, int entryId){
        return entryRepository.existsByFkPersonidPersonAndIdEntry(personId, entryId);
    }

    public EntryEntity createBlogEntry(int personId, EntryEntity blogEntry){
        blogEntry.setFkPersonidPerson(personId);
        EntryEntity savedEntry = entryRepository.save(blogEntry);
        return savedEntry;
    }


    public boolean deleteBlogEntry(int entryId, int personId){
        if(entryExistsForGivenPersonId(personId, entryId)){
            entryRepository.deleteById(entryId);
            return true;
        }
        return false;
    }


    public boolean editBlogEntry(int entryId, int personId, EntryEntity blogEntry){
        if(entryExistsForGivenPersonId(personId, entryId)){
            blogEntry.setFkPersonidPerson(personId);
            blogEntry.setIdEntry(entryId);
            entryRepository.save(blogEntry);
            return true;
        }
        return false;
    }


    public Iterable<EntryEntity> getBlogEntries(int personId){
        return entryRepository.findAllByFkPersonidPerson(personId);
    }

    
}
