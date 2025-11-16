package com.example.librarydemo.repository;

import com.example.librarydemo.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Transactional
    void deleteByTitle(String title);

    boolean existsByTitle(String title);
    boolean existsByAuthor(String author);
    boolean existsByTitleAndAuthor(String title, String author);

    Page<Book> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findAllByAuthorContainingIgnoreCase(String author, Pageable pageable);
    Page<Book> findAllByCategoryContainingIgnoreCase(String category, Pageable pageable);
}