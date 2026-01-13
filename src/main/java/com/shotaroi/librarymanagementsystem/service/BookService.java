package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.BookCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.BookUpdateRequest;
import com.shotaroi.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.*;

public interface BookService {
    Book create(BookCreateRequest req);

    Book update(Long id, BookUpdateRequest req);

    void delete(Long id);

    Book getById(Long id);

    Page<Book> list(Pageable pageable);
}
