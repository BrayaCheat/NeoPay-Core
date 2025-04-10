package NeoPay.Core.Controllers;

import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;
import NeoPay.Core.Services.ServiceImpl.TransactionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest dto){
        return ResponseEntity.status(201).body(transactionService.createTransaction(dto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long accountId){
        return ResponseEntity.status(200).body(transactionService.getTransaction(accountId));
    }
}
