package NeoPay.Core.Services;

import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest dto);
    List<TransactionResponse> getTransaction(Long accountId);
}
