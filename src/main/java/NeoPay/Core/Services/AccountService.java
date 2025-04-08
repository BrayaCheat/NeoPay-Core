package NeoPay.Core.Services;

import NeoPay.Core.DTO.Request.AccountRequest;
import NeoPay.Core.DTO.Response.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest dto);
    AccountResponse getAccount(Long accountId);
}
