package com.shotaroi.librarymanagementsystem.config;

import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.entity.Book;
import com.shotaroi.librarymanagementsystem.entity.Category;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import com.shotaroi.librarymanagementsystem.repository.BookRepository;
import com.shotaroi.librarymanagementsystem.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("!test") //  do not run during tests
public class DataSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public DataSeeder(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            CategoryRepository categoryRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // Avoid reseeding
        if (bookRepository.count() > 0) {
            return;
        }

        // =====================
        // CATEGORIES
        // =====================
        Category fiction = new Category();
        fiction.setName("Fiction");
        categoryRepository.save(fiction);

        Category fantasy = new Category();
        fantasy.setName("Fantasy");
        categoryRepository.save(fantasy);

        // =====================
        // AUTHORS
        // =====================
        Author orwell = new Author();
        orwell.setFullName("George Orwell");
        authorRepository.save(orwell);

        Author rowling = new Author();
        rowling.setFullName("J.K. Rowling");
        authorRepository.save(rowling);

        // =====================
        // BOOKS
        // =====================
        Book book1 = new Book();
        book1.setTitle("1984");
        book1.setIsbn("9780451524935");
        book1.setAuthor(orwell);
        book1.setCategory(fiction);
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setTitle("Harry Potter and the Philosopher's Stone");
        book2.setIsbn("9780747532699");
        book2.setAuthor(rowling);
        book2.setCategory(fantasy);
        book2.setAvailable(true);

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
