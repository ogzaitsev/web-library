package com.ogzaitsev.library.dto;

import lombok.Value;

import java.util.List;

@Value
public class ApiResponse<T> {
    List<T> content;
    Metadata metadata;

    public static ApiResponse<ClientReadingDto> of(List<ClientReadingDto> content, int pageSize) {
        Long nextId = content.get(content.size() - 1).getId();
        return new ApiResponse<>(content, new Metadata(nextId, pageSize));
    }

    @Value
    public static class Metadata {
        Long nextId;
        int pageSize;
    }
}
