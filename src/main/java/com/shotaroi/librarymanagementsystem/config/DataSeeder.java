package com.shotaroi.librarymanagementsystem.config;

import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Category;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {

        if (bookRepository.count() > 0) {
            return; // prevent duplicate seed
        }

        // ---------- AUTHORS ----------
        Author orwell = new Author();
        orwell.setName("George Orwell");

        Author rowling = new Author();
        rowling.setName("J.K. Rowling");

        authorRepository.save(orwell);
        authorRepository.save(rowling);

        // ---------- CATEGORIES ----------
        Category fiction = new Category();
        fiction.setName("Fiction");

        Category fantasy = new Category();
        fantasy.setName("Fantasy");

        categoryRepository.save(fiction);
        categoryRepository.save(fantasy);

        // ---------- BOOKS ----------
        Book book1 = Book.builder()
                .title("1984")
                .isbn("9780451524935")
                .author(orwell)
                .category(fiction)
                .available(true)
                .build();

        Book book2 = Book.builder()
                .title("Harry Potter")
                .isbn("9780439708180")
                .author(rowling)
                .category(fantasy)
                .available(true)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
