package com.shotaroi.librarymanagementsystem.service;

import com.shotaroi.librarymanagementsystem.dto.BookCreateRequest;
import com.shotaroi.librarymanagementsystem.dto.BookUpdateRequest;
import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
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

    @Override
    public Book create(BookCreateRequest req) {
        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Author not found with id" + req.authorId()
                        ));

        Book book = Book.builder()
                .title(req.title())
                .isbn(req.isbn())
                .author(author)
                .build();

        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookUpdateRequest req) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
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
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Author not found with id " + req.authorId()
                            ));
            book.setAuthor(author);
        }

        return bookRepository.save(book);
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
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Book not found with id " + id
                        ));
    }

    @Override
    public Page<Book> list(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
