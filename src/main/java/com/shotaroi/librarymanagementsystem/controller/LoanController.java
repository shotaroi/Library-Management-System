package com.shotaroi.librarymanagementsystem.controller;

import com.shotaroi.librarymanagementsystem.dto.LoanCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> borrow(@Valid @RequestBody LoanCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.borrow(req));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @GetMapping
    public ResponseEntity<Page<LoanResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(loanService.list(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<LoanResponse>> listActive(Pageable pageable) {
        return ResponseEntity.ok(loanService.listActive(pageable));
    }
}
