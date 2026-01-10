package com.shotaroi.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.lang.annotation.control.CodeGenerationHint;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
