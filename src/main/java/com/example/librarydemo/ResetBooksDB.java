package com.example.librarydemo;

import com.example.librarydemo.model.Book;
import com.example.librarydemo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResetBooksDB {
    private final BookRepository bookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final List<Book> bookList = new ArrayList<>(List.of(
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new Book("Pride and Prejudice", "Jane Austen", "Romance"),
            new Book("To Kill a Mockingbird", "Harper Lee", "Classic Literature")
    ));

    public ResetBooksDB(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void resetDB() {
        // Truncate the table and restart identity (resets IDs automatically)
        jdbcTemplate.execute("TRUNCATE TABLE book RESTART IDENTITY CASCADE");

        // Insert fresh data
        bookRepository.saveAll(bookList);

        System.out.println("Books DB has been reset!");
    }
}