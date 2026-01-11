package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.BookCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.BookResponse;
import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Category;
import com.shotaroi.librarymanagementsystem.exception.NotFoundException;
import com.shotaroi.librarymanagementsystem.mapper.BookMapper;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookResponse create(BookCreateRequest req) {
        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new NotFoundException("Author not found:" + req.authorId()));
        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Author not found: " + req.authorId()));
        Book book = Book.builder()
                .title(req.title())
                .author(author)
                .category(category)
                .available(true)
                .build();

        return BookMapper.toResponse(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> list(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Book> result = (title == null || title.isBlank())
                ? bookRepository.findAll(pageable)
                : bookRepository.findByTitleContainingIgnoreCase(title, pageable);

        return result.map(BookMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
        return BookMapper.toResponse(book);
    }
}
