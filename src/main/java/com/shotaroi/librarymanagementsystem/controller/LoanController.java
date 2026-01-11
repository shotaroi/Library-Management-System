package com.shotaroi.librarymanagementsystem.controller;

import com.shotaroi.librarymanagementsystem.dto.BorrowRequest;
import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books/{bookId}/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/borrow")
    public LoanResponse borrow (@PathVariable Long bookId, @Valid @RequestBody BorrowRequest req) {
        return loanService.borrow(bookId, req.borrowerName());
    }

    @PostMapping("/return")
    public LoanResponse returnBook (@PathVariable Long bookId) {
        return loanService.returnBook((bookId));
    }
}
