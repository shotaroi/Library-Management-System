package com.shotaroi.librarymanagementsystem.mapper;

import com.shotaroi.librarymanagementsystem.dto.BookResponse;
import com.shotaroi.librarymanagementsystem.entity.Book;

public class BookMapper {
    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getFullName(),
                book.getCategory().getName(),
                book.isAvailable()
        );
    }
}
