package com.example.librarydemo.controller;

import com.example.librarydemo.model.Book;
import com.example.librarydemo.repository.BookRepository;
import com.example.librarydemo.repository.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;

    public List<Book> booksList;

    public WishlistController(WishlistRepository wishlistRepository, BookRepository bookRepository, List<Book> booksList) {
        this.wishlistRepository = wishlistRepository;
        this.bookRepository = bookRepository;
        this.booksList = booksList;
    }

    @GetMapping
    public Page<Book> getBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        return wishlistRepository.findByWishlistedTrue(pageable);
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody List<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (booksList.contains(book)) {
                return ResponseEntity
                        .badRequest()
                        .body("Book already wishlisted");
            }

            if (book.getTitle() == null || book.getTitle().isBlank())
                return ResponseEntity
                        .badRequest()
                        .body("Book " + (i + 1) + " title is required!");
            if (book.getAuthor() == null || book.getAuthor().isBlank())
                return ResponseEntity
                        .badRequest()
                        .body("Book " + (i + 1) + " author is required!");

            if (!bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor()))
                return ResponseEntity
                        .badRequest()
                        .body("Book with title '" + (book.getTitle()) + "' " + "or author '" + (book.getAuthor()) + "' doesn't exist.");

            booksList.add(book);
            book.setWishlisted(true);

            // Force ID to null to ensure DB generates it
            book.setId(null);
        }

        wishlistRepository.saveAll(books);
        return ResponseEntity.ok("Books added to wishlist successfully!");
    }
}