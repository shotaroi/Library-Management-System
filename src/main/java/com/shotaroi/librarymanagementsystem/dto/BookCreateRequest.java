package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookCreateRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be <= 200 characters")
        String title,

        @NotNull(message = "authorId is required")
        Long authorId,

        @NotNull(message = "categoryId is required")
        Long categoryId,

        @NotNull(message = "ISBN is required")
        @Size(max = 20, message = "ISBN must be <= 20 characters")
        String isbn
) {}
