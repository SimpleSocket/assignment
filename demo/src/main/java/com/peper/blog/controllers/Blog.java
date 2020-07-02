package com.peper.blog.controllers;


import com.peper.blog.entities.EntryEntity;
import com.peper.blog.services.BlogService;
import com.peper.blog.utilities.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class Blog {

    @Autowired
    private BlogService blogService;

    @Autowired
    private SecurityUtils securityUtils;

    @PostMapping(value = "/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createEntry(@Valid @RequestBody EntryEntity entry){
        Integer id = securityUtils.getCurrentPrincipalId();
        blogService.createBlogEntry(id, entry);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/entries/{entryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEntry(@PathVariable("entryId") int entryId){
        Integer id = securityUtils.getCurrentPrincipalId();
        boolean opSuccessful = blogService.deleteBlogEntry(entryId, id);
        return opSuccessful ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/entries/{entryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editEntry(@PathVariable("entryId") int entryId, @Valid @RequestBody EntryEntity entry){
        Integer id = securityUtils.getCurrentPrincipalId();
        boolean opSuccessful = blogService.editBlogEntry(entryId, id, entry);
        return opSuccessful ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<EntryEntity>> getEntries(){
        Integer id = securityUtils.getCurrentPrincipalId();
        return ResponseEntity.ok(blogService.getBlogEntries(id));
    }

}
