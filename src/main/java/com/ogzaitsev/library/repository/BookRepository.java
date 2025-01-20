package com.ogzaitsev.library.repository;

import com.ogzaitsev.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = " SELECT * " +
                   " FROM books " +
                   " WHERE (:lastBookId IS NULL OR book_id > :lastBookId) " +
                   " ORDER BY book_id ASC " +
                   " LIMIT :limit",
            nativeQuery = true)
    List<Book> findAll(@Param("lastBookId") Long lastBookId, @Param("limit") int limit);
}
