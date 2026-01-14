package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.BookCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.BookResponse;
import com.shotaroi.librarymanagementsystem.dto.BookUpdateRequest;
import com.shotaroi.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookCreateRequest req);
    BookResponse update(Long id, BookUpdateRequest req);
    void delete(Long id);
    BookResponse getById(Long id);
    Page<BookResponse> list(Pageable pageable);
    Page<BookResponse> searchByTitle(String query, Pageable pageable);
}
