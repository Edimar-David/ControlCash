package com.novaStack.backend.dto.transactions;

import java.math.BigDecimal;

public record SummaryDTO(
        Long totalTransactions,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal totalBalance
        ) {
}
