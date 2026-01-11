package com.shotaroi.librarymanagementsystem.dto;

import java.time.Instant;

public record LoanResponse(
        Long id,
        Long bookId,
        String bookTitle,
        String borrowerName,
        Instant borrowedAt,
        Instant returnedAt
) {}
