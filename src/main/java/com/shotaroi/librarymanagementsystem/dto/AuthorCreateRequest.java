package com.shotaroi.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateRequest(
        @NotBlank(message = "name is required")
        String name
) {}
