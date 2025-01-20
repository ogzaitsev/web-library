package com.ogzaitsev.library.dto;

import com.ogzaitsev.library.entity.Book;
import lombok.Data;

@Data()
public class BookDto {
    private final long id;
    private final String title;
    private final String author;
    private final String isbn;

    public static BookDto of(Book entity) {
        return new BookDto(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
    }
}
