package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.LoanCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {
    LoanResponse borrow(LoanCreateRequest req);
    LoanResponse returnBook(Long loanId);
    Page<LoanResponse> list(Pageable pageable);
    List<LoanResponse> listActive();
}
