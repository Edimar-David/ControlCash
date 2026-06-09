package com.novaStack.backend.service;

import com.novaStack.backend.DTO.SummaryDTO;
import com.novaStack.backend.DTO.TransactionRequestDTO;
import com.novaStack.backend.DTO.TransactionResponseDTO;
import com.novaStack.backend.model.TYPE;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.TransactionRepository;
import com.novaStack.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {


    @PersistenceContext
    EntityManager em;
    @Autowired
    TransactionRepository repository;
    @Autowired
    UserRepository userRepository;

    public TransactionResponseDTO create(TransactionRequestDTO dto) {

            if(dto.amount().compareTo(BigDecimal.ZERO) > 0) {

                User user = this.findUser();
                Transaction transaction = new Transaction(dto, user);
                repository.save(transaction);
                TransactionResponseDTO responseDTO = new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getCategory(),
                        transaction.getDate()
                );
                return responseDTO;
            }
            return null;
    }

    public @Nullable List<Transaction> findAll() {
        User user = this.findUser();
        List<Transaction> allTransactions = repository.findByUser(user);
        return allTransactions;
    }


    public void delete(Long id) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new RuntimeException());
        repository.delete(transaction);

    }

    public TransactionResponseDTO findById(Long id) {
        Optional<Transaction> transactionOptional = repository.findById(id);
            Transaction transaction = transactionOptional.get();
            TransactionResponseDTO dto = new TransactionResponseDTO(
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getDescription(),
                    transaction.getAmount(),
                    transaction.getCategory(),
                    transaction.getDate());
            return dto;

    }

    public @Nullable TransactionResponseDTO update(Long id, TransactionRequestDTO dto) {
        Transaction transaction = repository.findById(id).orElseThrow();

        transaction.setDescription(dto.description());
        transaction.setDate(dto.date());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setCategory(dto.category());

        repository.save(transaction);

        return null;
    }

    public SummaryDTO summary(/*int month, int year*/) {

        User user = this.findUser();

//        LocalDate start = LocalDate.of(year, month, 1);
//        LocalDate end = start.plusMonths(1);

        SummaryDTO summary = repository.findSummary(user /*, start, end */);
        System.out.println(summary);
        return summary;
    }

    public List<TransactionResponseDTO> getPageTransactions(LocalDate startDate, LocalDate endDate, TYPE type, Integer page, Integer size) {
        User user = this.findUser();
        if(page == null) page = 0;
        if(size == null) size = 10;
        Pageable pageable = PageRequest.of(page, size);

        StringBuilder jpql = new StringBuilder("SELECT t FROM Transaction t WHERE ");
        jpql.append("t.user = :user");
        if (startDate != null) {
            jpql.append(" AND t.date > :startDate");
        }

        if (endDate != null) {
            jpql.append(" AND t.date < :endDate");
        }

        if (type != null) {
            jpql.append(" AND t.type = :type");
        }
            jpql.append(" ORDER BY t.date DESC");

        TypedQuery<Transaction> query = em.createQuery(jpql.toString(), Transaction.class);

        query.setParameter("user", user);
        if (startDate != null) query.setParameter("startDate", startDate);
        if (endDate != null) query.setParameter("endDate", endDate);
        if (type != null) query.setParameter("type", type);


        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Transaction> transactions = query.getResultList();

        List<TransactionResponseDTO> transactionsDTO =  transactions.stream().map(
                t -> new TransactionResponseDTO(
                                            t.getId(),
                                            t.getType(),
                                            t.getDescription(),
                                            t.getAmount(),
                                            t.getCategory(),
                                            t.getDate()
                                            ))
                .collect(Collectors.toList());

        transactionsDTO.forEach(System.out::println);
        return transactionsDTO;
    }

    private User findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUser = authentication.getName();
        User user = (User) authentication.getPrincipal();
        return user;
    }
}
