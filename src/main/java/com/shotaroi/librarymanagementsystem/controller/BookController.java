package com.shotaroi.librarymanagementsystem.controller;

import com.shotaroi.librarymanagementsystem.dto.BookCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.BookResponse;
import com.shotaroi.librarymanagementsystem.dto.BookUpdateRequest;
import com.shotaroi.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookCreateRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    // GET /api/books?page=0&size=10&sort=title,asc
    @GetMapping
    public ResponseEntity<Page<BookResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(bookService.list(pageable));
    }

    // GET /api/books/search?title=harry&page=0&size=10
    @GetMapping("/search")
    public ResponseEntity<Page<BookResponse>> searchByTitle(
            @RequestParam String title,
            Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.searchByTitle(title, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateRequest request
    ) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
