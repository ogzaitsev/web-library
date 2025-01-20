package com.ogzaitsev.library.repository;

import com.ogzaitsev.library.dto.ClientReadingDto;
import com.ogzaitsev.library.entity.BorrowedBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingsRepository extends JpaRepository<BorrowedBook, Long> {
    Optional<BorrowedBook> findByClientIdAndBookId(Long clientId, Long bookId);

    @Query(value = "        SELECT bb\n" +
                   "        FROM BorrowedBook bb\n" +
                   "        WHERE (:lastId IS NULL OR bb.id > :lastId)\n" +
                   "        ORDER BY bb.id ASC ", nativeQuery = false)
    List<BorrowedBook> findReadingClients(@Param("lastId") Long lastId, Pageable pageable);
}
