package com.ogzaitsev.library.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ClientReadingDto {
    private final Long id;
    private final String clientName;
    private final String clientBirthDate;
    private final String bookTitle;
    private final String bookAuthor;
    private final String bookIsbn;
    private final String borrowedAt;

    public ClientReadingDto(Long id, String clientName, LocalDate clientBirthDate, String bookTitle,
                            String bookAuthor, String bookIsbn, LocalDateTime borrowedAt) {
        this.id = id;
        this.clientName = clientName;
        this.clientBirthDate = clientBirthDate.toString();
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookIsbn = bookIsbn;
        this.borrowedAt = borrowedAt.toString();
    }
}
