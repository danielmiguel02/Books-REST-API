package com.example.librarydemo.repository;

import com.example.librarydemo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Book, Long> {
    Page<Book> findByWishlistedTrue(Pageable pageable);
}
