package com.ogzaitsev.library.dto;

import com.ogzaitsev.library.entity.Client;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class ClientDto {
    private final Long id;
    private final String name;
    private final LocalDate birthDate;

    public static ClientDto of(Client entity) {
        return new ClientDto(entity.getId(), entity.getName(), entity.getBirthDate());
    }
}
