package com.rest.app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findbyId(Long bookId);
}
