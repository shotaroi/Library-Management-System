package com.shotaroi.librarymanagementsystem.controller;

import com.shotaroi.librarymanagementsystem.dto.LoanCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Loan;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<LoanResponse> borrow(@Valid @RequestBody LoanCreateRequest req) {
        Book book = bookRepository.findById(req.bookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (!book.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book is not available");
        }

        book.setAvailable(true); // <-- if you use available=true meaning available, set false here
        // IMPORTANT: Most systems mean available=true => available to borrow.
        // When borrowed, you should set available=false:
        book.setAvailable(false);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setBorrowerName(req.borrowerName());
        loan.setBorrowedAt(Instant.now());
        loan.setReturnedAt(null);

        bookRepository.save(book);
        Loan saved = loanRepository.save(loan);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        if (loan.getReturnedAt() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan already returned");
        }

        loan.setReturnedAt(Instant.now());

        Book book = loan.getBook();
        if (book != null) {
            book.setAvailable(true);
            bookRepository.save(book);
        }

        Loan saved = loanRepository.save(loan);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> list() {
        return ResponseEntity.ok(
                loanRepository.findAll().stream().map(this::toResponse).toList()
        );
    }

    private LoanResponse toResponse(Loan loan) {
        Book book = loan.getBook();
        return new LoanResponse(
                loan.getId(),
                book != null ? book.getId() : null,
                book != null ? book.getTitle() : null,
                loan.getBorrowerName(),
                loan.getBorrowedAt(),
                loan.getReturnedAt()
        );
    }
}
