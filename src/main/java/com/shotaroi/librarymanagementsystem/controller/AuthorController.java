package com.shotaroi.librarymanagementsystem.controller;

import com.shotaroi.librarymanagementsystem.dto.AuthorCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.AuthorResponse;
import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorCreateRequest req) {
        Author author = new Author();
        author.setName(req.name());

        Author saved = authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthorResponse(saved.getId(), saved.getName()));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> list() {
        List<AuthorResponse> res = authorRepository.findAll()
                .stream()
                .map(a -> new AuthorResponse(a.getId(), a.getName()))
                .toList();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> get(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(a -> ResponseEntity.ok(new AuthorResponse(a.getId(), a.getName())))
                .orElse(ResponseEntity.notFound().build());
    }
}
