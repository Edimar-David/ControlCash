package com.novaStack.backend.service;

import com.novaStack.backend.DTO.SummaryDTO;
import com.novaStack.backend.DTO.TransactionRequestDTO;
import com.novaStack.backend.DTO.TransactionResponseDTO;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.TransactionRepository;
import com.novaStack.backend.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;
    @Autowired
    UserRepository userRepository;
    public TransactionResponseDTO create(TransactionRequestDTO dto) {

            if(dto.amount().compareTo(BigDecimal.ZERO) > 0) {

                Optional<User> user = this.findUser();
                Transaction transaction = new Transaction(dto, user.get());
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
        Optional<User> user = this.findUser();
        List<Transaction> allTransactions = repository.findByUser(user.get());
        return allTransactions;
    }


    public void delete(Long id) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new RuntimeException());
        repository.delete(transaction);

    }

    public TransactionResponseDTO findById(Long id) {
        Optional<Transaction> transactionOptional = repository.findById(id);
        if(transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            TransactionResponseDTO dto = new TransactionResponseDTO(
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getDescription(),
                    transaction.getAmount(),
                    transaction.getCategory(),
                    transaction.getDate());
            return dto;
        }else{
            return null;
        }
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

    public SummaryDTO summary(int month, int year) {

        User user = this.findUser().get();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);

        SummaryDTO summary = repository.findSummary(user, start, end);
        System.out.println(summary);
        return summary;
    }

    private Optional<User> findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUser = authentication.getName();
        Optional<User> user = userRepository.findByEmail(emailUser);
        return user;
    }
}
