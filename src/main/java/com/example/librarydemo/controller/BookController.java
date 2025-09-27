package com.example.librarydemo.controller;

import com.example.librarydemo.model.Book;
import com.example.librarydemo.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Page<Book> getBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        return bookRepository.findAll(pageable);
    }

//    private static PageRequest getPageable(int page, int size) {
//        return PageRequest.of(page, size);
//    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getBookByTitle(
            @PathVariable String title,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        if (title == null || title.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Title is required!");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAllByTitleContainingIgnoreCase(title, pageable);

        if (books.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Book with title \"" + title + "\" doesn't exist!");
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBookByAuthor(
            @PathVariable String author,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        if (author == null || author.isBlank())
            return ResponseEntity
                    .badRequest()
                    .body("Author is required!");

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAllByAuthorContainingIgnoreCase(author, pageable);

        if (books.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Book with author \"" + author + "\" doesn't exist.");

        return ResponseEntity.ok(books);
    }

    // Implement getting by category / implement category
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getBookByCategory(
            @PathVariable String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        if (category == null || category.isBlank())
            return ResponseEntity
                    .badRequest()
                    .body("Category is required!");

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAllByCategoryContainingIgnoreCase(category, pageable);

        if (books.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Book with category \"" + category + "\" doesn't exist.");

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<String> addBooks(@RequestBody List<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (book.getTitle() == null || book.getTitle().isBlank())
                return ResponseEntity
                        .badRequest()
                        .body("Book " + (i + 1) + " title is required!");
            if (book.getAuthor() == null || book.getAuthor().isBlank())
                return ResponseEntity
                        .badRequest()
                        .body("Book " + (i + 1) + " author is required!");

            // Force ID to null to ensure DB generates it
            book.setId(null);
        }

        // Save books
        bookRepository.saveAll(books);
        return ResponseEntity.ok("Books added successfully!");
    }

    @PatchMapping
    public ResponseEntity<String> updateBooks(@RequestBody List<Book> books) {
        for (Book book : books) {
            if (book.getId() == null) {
                // Return 400 Bad Request with a message
                return ResponseEntity
                        .badRequest()
                        .body("Book ID is required.");
            }

            boolean exists = bookRepository.existsById(book.getId());
            if (!exists) {
                // Return 400 Bad Request with a message
                return ResponseEntity
                        .badRequest()
                        .body("Book ID doesn't exist.");
            }

            if (book.getTitle() == null || book.getTitle().isBlank())
                return ResponseEntity
                        .badRequest()
                        .body("Book with ID " + book.getId() + " requires a title!");
            if (book.getAuthor() == null || book.getAuthor().isBlank())
                return ResponseEntity
                        .badRequest()
                        .body("Book with ID " + book.getId() + " requires an author!");
        }

        // Save book
        bookRepository.saveAll(books);

        // Return 200 OK with success message
        return ResponseEntity.ok("Books updated successfully!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBooks(@RequestBody List<Book> books) {
        for (Book book : books) {
            // Must provide ID or title
            if (book.getId() == null && (book.getTitle() == null || book.getTitle().isBlank())) {
                return ResponseEntity
                        .badRequest()
                        .body("You need Book ID or Book Title to delete a book.");
            }

            if (book.getId() != null) {
                if (!bookRepository.existsById(book.getId())) {
                    return ResponseEntity
                            .badRequest()
                            .body("Book with ID " + book.getId() + " doesn't exist.");
                }
                bookRepository.deleteById(book.getId());
            } else {
                if (!bookRepository.existsByTitle(book.getTitle())) {
                    return ResponseEntity
                            .badRequest()
                            .body("Book with Title " + book.getTitle() + " doesn't exist.");
                }
                bookRepository.deleteByTitle(book.getTitle());
            }
        }

        // Return 200 OK with success message
        return ResponseEntity.ok("Books deleted successfully!");
    }
}
