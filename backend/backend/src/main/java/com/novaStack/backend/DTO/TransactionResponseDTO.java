package com.novaStack.backend.DTO;

import com.novaStack.backend.model.TYPE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        TYPE type,
        String description,
        BigDecimal amount,
        String category,
        LocalDate date) {
}
