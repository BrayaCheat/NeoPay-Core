package NeoPay.Core.Controllers;

import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;
import NeoPay.Core.Services.ServiceImpl.TransactionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest dto) {
        return ResponseEntity.status(201).body(transactionService.createTransaction(dto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sortBy = direction.equalsIgnoreCase("desc") ?
                Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size, sortBy);
        return ResponseEntity.status(200).body(transactionService.getTransaction(accountId, pageable));
    }

    @GetMapping("/{accountId}/receiver")
    public ResponseEntity<Page<TransactionResponse>> getReceiveTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String senderAccountNumber,
            @RequestParam(required = false) String receiverAccountNumber
    ) {
        Sort sortBy = direction.equalsIgnoreCase("desc") ?
                Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size, sortBy);

        return ResponseEntity.ok(
                transactionService.getReceiveTransactions(accountId, pageable, senderAccountNumber, receiverAccountNumber)
        );
    }

    @GetMapping("/{accountId}/sender")
    public ResponseEntity<Page<TransactionResponse>> getSenderTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String senderAccountNumber,
            @RequestParam(required = false) String receiverAccountNumber
    ) {
        Sort sortBy = direction.equalsIgnoreCase("desc") ?
                Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size, sortBy);

        return ResponseEntity.ok(
                transactionService.getSenderTransactions(accountId, pageable, senderAccountNumber, receiverAccountNumber)
        );
    }

    @GetMapping("/{accountId}/transaction-count")
    public ResponseEntity<Long> transactionCount(@PathVariable Long accountId){
        return ResponseEntity.status(200).body(transactionService.transactionCount(accountId));
    }
}
