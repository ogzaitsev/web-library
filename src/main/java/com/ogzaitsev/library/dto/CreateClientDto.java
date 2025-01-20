package com.ogzaitsev.library.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CreateClientDto {
    private final String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
}
