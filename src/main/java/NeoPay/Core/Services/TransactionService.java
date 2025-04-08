package NeoPay.Core.Services;

import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest dto);
}
