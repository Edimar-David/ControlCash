package com.novaStack.backend.DTO;

import java.math.BigDecimal;

public record SummaryDTO(
        Long totalTransactions,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal totalBalance
        ) {
}
