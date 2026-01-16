package com.shotaroi.librarymanagementsystem.controller;

import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<Author> create(@Valid @RequestBody Author author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorRepository.save(author));
    }

    @GetMapping
    public ResponseEntity<List<Author>> list() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> get(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
