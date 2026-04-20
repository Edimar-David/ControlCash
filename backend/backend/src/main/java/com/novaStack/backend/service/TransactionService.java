package com.novaStack.backend.service;

import com.novaStack.backend.DTO.TransactionRequestDTO;
import com.novaStack.backend.DTO.TransactionResponseDTO;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.TransactionRepository;
import com.novaStack.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;
    @Autowired
    UserRepository userRepository;

    public TransactionResponseDTO create(TransactionRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUser = authentication.getName();
        Optional<User> user = userRepository.findByEmail(emailUser);
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

    public @Nullable List<Transaction> findAll(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUser = authentication.getName();

        User user = userRepository.findByEmail(emailUser).orElseThrow(() -> new RuntimeException());
        List<Transaction> allTransactions = repository.findByUser(user);
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
}
