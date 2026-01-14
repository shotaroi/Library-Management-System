package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.Size;

public record BookUpdateRequest(
        @Size(max = 200, message = "Title must be <= 200 characters")
        String title,

        Long authorId,

        Long categoryId,

        @Size(max = 20, message = "ISBN must be <= 20 characters")
        String isbn
) {}
