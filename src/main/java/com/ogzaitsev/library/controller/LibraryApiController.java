package com.ogzaitsev.library.controller;

import com.ogzaitsev.library.dto.ApiResponse;
import com.ogzaitsev.library.dto.ClientReadingDto;
import com.ogzaitsev.library.service.BorrowingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LibraryApiController {
    private final BorrowingsService borrowingsService;

    @Autowired
    public LibraryApiController(BorrowingsService borrowingsService) {
        this.borrowingsService = borrowingsService;
    }

    @GetMapping("api")
    public ResponseEntity<ApiResponse<ClientReadingDto>> getAllReadingClients(
            @RequestParam(required = false) LocalDateTime lastBorrowedAt,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<ClientReadingDto> readingClients = borrowingsService.getReadingClients(lastId, pageSize);
        ApiResponse<ClientReadingDto> response = ApiResponse.of(readingClients, pageSize);
        return ResponseEntity.ok(response);
    }
}
