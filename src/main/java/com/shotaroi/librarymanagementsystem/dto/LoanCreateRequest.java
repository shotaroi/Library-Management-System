package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanCreateRequest(
        @NotNull(message = "bookId is required")
        Long bookId,
        @NotBlank(message = "borrowerName is required")
        String borrowerName
) {}
