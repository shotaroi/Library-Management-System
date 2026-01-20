package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.LoanCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Loan;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public LoanResponse borrow(LoanCreateRequest req) {
        Book book = bookRepository.findByIdForUpdate(req.bookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        // Rule: cannot borrow if already borrowed
        if (loanRepository.existsByBookIdAndReturnedAtIsNull(book.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book is already borrowed");
        }

        if (!book.isAvailable()) {
            // optional: keep this as a secondary guard (in case available got out of sync)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book is not available");
        }

        book.setAvailable(false);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setBorrowerName(req.borrowerName());
        loan.setBorrowedAt(Instant.now());
        loan.setReturnedAt(null);

        bookRepository.save(book);
        Loan saved = loanRepository.save(loan);

        return toResponse(saved);
    }

    @Transactional
    @Override
    public LoanResponse returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
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
        return toResponse(saved);
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
