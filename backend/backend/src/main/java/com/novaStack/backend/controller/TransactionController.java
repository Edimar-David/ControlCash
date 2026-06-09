package com.novaStack.backend.controller;

import com.novaStack.backend.DTO.SummaryDTO;
import com.novaStack.backend.DTO.TransactionRequestDTO;
import com.novaStack.backend.DTO.TransactionResponseDTO;
import com.novaStack.backend.model.TYPE;
import com.novaStack.backend.model.Transaction;
import com.novaStack.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

//    @GetMapping
//    public ResponseEntity<List<Transaction>> findAll() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryDTO> getSummary(/*@RequestParam int month, @RequestParam int year */) {

       SummaryDTO summary = service.summary(/*month, year*/);
        return ResponseEntity.ok().body(summary);
    }

    @GetMapping
    public List<TransactionResponseDTO> getPageTransactions(@RequestParam (required = false) LocalDate startDate,
                                                            @RequestParam (required = false) LocalDate endDate,
                                                            @RequestParam (required = false) TYPE type,
                                                            @RequestParam (required = false) Integer page,
                                                            @RequestParam (required = false) Integer size){

        return service.getPageTransactions(startDate, endDate, type, page, size);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            TransactionResponseDTO responseDTO = service.findById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
        }catch (RuntimeException e) {
            //new RuntimeException(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/{id:\\d+}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id, @RequestBody TransactionRequestDTO dto) {

        TransactionResponseDTO transaction = service.update(id, dto);
        return ResponseEntity.ok().body(transaction);
    }


    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();


    }

}
