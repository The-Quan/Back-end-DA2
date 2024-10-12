package org.example.doanki2.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news-books/")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsBookController {

    private final NewsBookService newsBookService;

    @Autowired
    public NewsBookController(NewsBookService newsBookService) {
        this.newsBookService = newsBookService;
    }

    @GetMapping
    public ResponseEntity<List<News_Book>> getAll() {
        return newsBookService.getAll();
    }

    @GetMapping("/false")
    public ResponseEntity<List<News_Book>> getAllFalse() {
        return newsBookService.getAllFalse();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public String fetchAndSaveBooks() {
        newsBookService.fetchAndSaveBooks();
        return "Books fetched and saved!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<News_Book> delete(@PathVariable Integer id) {
        return newsBookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<News_Book> put(@RequestBody News_Book newsBook, @PathVariable Integer id) {
        return newsBookService.update(id, newsBook);
    }
}
