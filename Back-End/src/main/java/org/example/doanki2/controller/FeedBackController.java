package org.example.doanki2.controller;

import org.example.doanki2.entity.Feed_Back;
import org.example.doanki2.model.feedBack.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback/")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @GetMapping
    public ResponseEntity<List<Feed_Back>> getAll(){
        return feedBackService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Feed_Back>> getById(@PathVariable Integer id){
        return feedBackService.getById(id);
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Feed_Back> post (@RequestBody Feed_Back feedBack){
        return feedBackService.post(feedBack);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Feed_Back> put(@PathVariable Integer id, @RequestBody Feed_Back feedBack){
        return feedBackService.put(id, feedBack);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Feed_Back> delete(@PathVariable Integer id){
        return feedBackService.Delete(id);
    }
}
