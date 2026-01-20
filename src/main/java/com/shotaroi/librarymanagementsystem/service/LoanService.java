package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.LoanCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.LoanResponse;

import java.util.List;

public interface LoanService {
    LoanResponse borrow(LoanCreateRequest req);
    LoanResponse returnBook(Long loanId);
    List<LoanResponse> list();
}
