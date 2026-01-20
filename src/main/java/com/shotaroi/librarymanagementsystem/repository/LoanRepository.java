package com.shotaroi.librarymanagementsystem.repository;

import com.shotaroi.librarymanagementsystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);
    List<Loan> findByReturnedAtIsNull();

}
