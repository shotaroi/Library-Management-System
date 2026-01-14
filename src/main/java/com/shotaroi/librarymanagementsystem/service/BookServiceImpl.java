package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.BookCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.BookResponse;
import com.shotaroi.librarymanagementsystem.dto.BookUpdateRequest;
import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Category;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponse create(BookCreateRequest req) {
        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Author not found with id " + req.authorId()
                ));

        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id " + req.categoryId()
                ));

        Book book = Book.builder()
                .title(req.title())
                .isbn(req.isbn())
                .author(author)
                .category(category)
                .build();

        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    @Override
    public BookResponse update(Long id, BookUpdateRequest req) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found with id " + id
                ));

        if (req.title() != null) {
            book.setTitle(req.title());
        }

        if (req.isbn() != null) {
            book.setIsbn(req.isbn());
        }

        if (req.authorId() != null) {
            Author author = authorRepository.findById(req.authorId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Author not found with id " + req.authorId()
                    ));
            book.setAuthor(author);
        }

        if (req.categoryId() != null) {
            Category category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Category not found with id " + req.categoryId()
                    ));
            book.setCategory(category);
        }

        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book not found with id " + id
            );
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found with id " + id
                ));
        return toResponse(book);
    }

    @Override
    public Page<BookResponse> list(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public Page<BookResponse> searchByTitle(String query, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(query, pageable).map(this::toResponse);
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor() != null ? book.getAuthor().getName() : null,
                book.getCategory() != null ? book.getCategory().getName() : null,
                book.isAvailable()
        );
    }
}
