package com.shotaroi.librarymanagementsystem.mapper;

import com.shotaroi.librarymanagementsystem.dto.BookResponse;
import com.shotaroi.librarymanagementsystem.entity.Book;

public final class BookMapper {
    private BookMapper() {}

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor().getName(),
                book.getCategory().getName(),
                book.isAvailable()
        );
    }

}
