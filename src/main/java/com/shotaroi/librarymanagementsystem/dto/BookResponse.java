package com.shotaroi.librarymanagementsystem.dto;

public record BookResponse(
        Long id,
        String title,
        String authorName,
        String categoryName,
        boolean available
) {}
