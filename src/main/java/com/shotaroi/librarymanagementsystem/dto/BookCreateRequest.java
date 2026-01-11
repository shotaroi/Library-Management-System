package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateRequest(
        @NotBlank String title,
        @NotNull Long authorId,
        @NotNull Long categoryId
) {}
