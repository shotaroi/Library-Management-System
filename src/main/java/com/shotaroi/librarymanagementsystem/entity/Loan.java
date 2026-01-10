package com.shotaroi.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "loans")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private String borrowerName;

    @Column(nullable = false)
    private Instant borrowedAt;

    private Instant returnedAt;

    public boolean isActive() {
        return returnedAt == null;
    }
}

