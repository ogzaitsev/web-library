package com.ogzaitsev.library.dto;

import lombok.Data;

@Data
public class CreateBookDto {
    private final String title;
    private final String author;
    private final String isbn;
}
