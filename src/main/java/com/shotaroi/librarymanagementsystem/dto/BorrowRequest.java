package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public record BorrowRequest(@NotBlank String borrowerName) {
}
