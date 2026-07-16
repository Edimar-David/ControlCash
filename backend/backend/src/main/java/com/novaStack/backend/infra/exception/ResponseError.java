package com.novaStack.backend.infra.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseError(
        String message,
        HttpStatus status,
        LocalDateTime timestamp
) {
}