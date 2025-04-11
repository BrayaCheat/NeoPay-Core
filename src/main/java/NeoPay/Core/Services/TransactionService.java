package NeoPay.Core.Services;

import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest dto);
    List<TransactionResponse> getTransaction(Long accountId, Pageable pageable);
    Page<TransactionResponse> getReceiveTransactions(Long accountId, Pageable pageable, String senderAccountNumber, String receiverAccountNumber);
    Page<TransactionResponse> getSenderTransactions(Long accountId, Pageable pageable, String senderAccountNumber, String receiverAccountNumber);
}
