package com.novaStack.backend.repository;

import com.novaStack.backend.DTO.SummaryDTO;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

@Query("""
    SELECT new com.novaStack.backend.DTO.SummaryDTO(
        COUNT(t) as totalTransactions,
        
        COALESCE(SUM(CASE WHEN t.type = 'INCOME' then t.amount
        ELSE 0 END), 0) AS totalIncome,

        COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' then t.amount
        ELSE 0 END),0) AS totalExpense,

        COALESCE(SUM(CASE WHEN t.type = 'INCOME'	THEN t.amount WHEN t.type = 'EXPENSE'
        THEN -t.amount ELSE 0 END), 0) AS totalBalance
    )
    FROM Transaction t
        WHERE t.date < :end
        AND t.date >= :start 
        AND t.user = :user""")
SummaryDTO findSummary(User user, LocalDate start, LocalDate end);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.date >= :start AND t.date <  :end")
    List<Transaction> findByDate(Long userId, LocalDate start, LocalDate end);
}
