package com.novaStack.backend.service;

import com.novaStack.backend.DTO.TransactionRequestDTO;
import com.novaStack.backend.DTO.TransactionResponseDTO;
import com.novaStack.backend.model.TYPE;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.TransactionRepository;
import com.novaStack.backend.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;
    @Autowired
    UserRepository userRepository;

    public TransactionResponseDTO create(TransactionRequestDTO dto) {
        String emailUser = "edimar@gmail.com";
        Optional<User> user = userRepository.findByEmail(emailUser);
        Transaction transaction = new Transaction(dto, user.get());
        repository.save(transaction);
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(
                transaction.getId(),
                transaction.getType(),
                transaction.getName(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getCategory(),
                transaction.getDate()
        );
        return responseDTO;
    }

//    public List<TransactionResponseDTO> findAll() {
//
//    }

    public TransactionResponseDTO findById(Long id) {
        Optional<Transaction> transactionOptional = repository.findById(id);
        if(transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            TransactionResponseDTO dto = new TransactionResponseDTO(
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getName(),
                    transaction.getDescription(),
                    transaction.getValue(),
                    transaction.getCategory(),
                    transaction.getDate());
            return dto;
        }else{
            return null;
        }
    }

    public @Nullable List<TransactionResponseDTO> findAll(String token) {
        return null;
    }

}
