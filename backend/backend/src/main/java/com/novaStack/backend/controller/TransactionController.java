package com.novaStack.backend.controller;

import com.novaStack.backend.DTO.TransactionRequestDTO;
import com.novaStack.backend.DTO.TransactionResponseDTO;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody TransactionRequestDTO dto){
        TransactionResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(service.findAll("edimar"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id, @RequestBody TransactionRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
