package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanCreateRequest(
        @NotNull Long bookId,
        @NotBlank String borrowerName
) {}
