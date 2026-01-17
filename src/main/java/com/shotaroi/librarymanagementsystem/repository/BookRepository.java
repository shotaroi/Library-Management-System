package com.shotaroi.librarymanagementsystem.repository;

import com.shotaroi.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.EntityGraph;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "category"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "category"})
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    boolean existsByIsbn(String isbn);
}

