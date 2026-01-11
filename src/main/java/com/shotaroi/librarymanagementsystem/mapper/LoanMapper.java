package com.shotaroi.librarymanagementsystem.mapper;

import com.shotaroi.librarymanagementsystem.dto.LoanResponse;
import com.shotaroi.librarymanagementsystem.entity.Loan;

public class LoanMapper {
    public static LoanResponse toResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getBorrowerName(),
                loan.getBorrowedAt(),
                loan.getReturnedAt()
        );
    }
}
