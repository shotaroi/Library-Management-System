package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Loan;
import com.shotaroi.librarymanagementsystem.exception.BadRequestException;
import com.shotaroi.librarymanagementsystem.exception.NotFoundException;
import com.shotaroi.librarymanagementsystem.mapper.LoanMapper;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Transactional
    public LoanResponse borrow(Long bookId, String borrowerName) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found: " + bookId));

        if (!book.isAvailable()) {
            throw new BadRequestException("Book is not available for borrowing.");
        }

        book.setAvailable(false);

        Loan loan = Loan.builder()
                .book(book)
                .borrowerName(borrowerName)
                .borrowedAt(Instant.now())
                .build();

        loanRepository.save(loan);
        return LoanMapper.toResponse(loan);
    }

    @Transactional
    public LoanResponse returnBook(Long bookId) {
        Loan activeLoan = loanRepository.findByBookIdAndReturnedAtIsNull(bookId)
                .orElseThrow(() -> new BadRequestException("This book is not currently borrowed."));

        Book book = activeLoan.getBook();
        book.setAvailable(true);

        activeLoan.setReturnedAt(Instant.now());
        return LoanMapper.toResponse(activeLoan);
    }
}
