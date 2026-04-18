package com.novaStack.backend.DTO;

import com.novaStack.backend.model.TYPE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequestDTO(
        TYPE type,
        String name,
        String description,
        BigDecimal value,
        String category,
        LocalDateTime date) {
}
