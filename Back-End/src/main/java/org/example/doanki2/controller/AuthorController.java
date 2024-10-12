package org.example.doanki2.controller;

import org.example.doanki2.entity.Author;
import org.example.doanki2.model.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author/v1/")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAll(){
            return authorService.getAllAuthor();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Author> getById(@PathVariable Integer id){
        return authorService.getByIdAuthor(id);
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Author> post(@RequestBody Author author){
        return authorService.addAuthor(author);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Author> put(@PathVariable Integer id , @RequestBody Author author){
        return authorService.updateAuthor(id, author);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Author> delete(@PathVariable Integer id){
        return authorService.deleteAuthor(id);
    }
}
