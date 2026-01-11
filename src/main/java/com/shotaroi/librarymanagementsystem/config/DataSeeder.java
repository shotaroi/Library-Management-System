package com.shotaroi.librarymanagementsystem.config;

import com.shotaroi.librarymanagementsystem.entity.Author;
import com.shotaroi.librarymanagementsystem.entity.Category;
import com.shotaroi.librarymanagementsystem.repository.AuthorRepository;
import com.shotaroi.librarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run (String... args) {
        if (authorRepository.count() == 0) {
            authorRepository.save(Author.builder().fullName("George Orwell").build());
            authorRepository.save(Author.builder().fullName("Jane Austen").build());
        }
        if (categoryRepository.count() == 0) {
            categoryRepository.save(Category.builder().name("Fiction").build());
            categoryRepository.save(Category.builder().name("Classic").build());
        }
    }
}
