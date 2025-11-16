package com.example.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String category;
    private boolean wishlisted;

    // Default constructor
    public Book() {}

    // Constructor with fields
    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isWishlisted() { return wishlisted; }
    public void setWishlisted(boolean wishlisted) { this.wishlisted = wishlisted; }
}