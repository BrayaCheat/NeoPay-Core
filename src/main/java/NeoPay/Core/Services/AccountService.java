package NeoPay.Core.Services;

import NeoPay.Core.DTO.Request.AccountRequest;
import NeoPay.Core.DTO.Response.AccountResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {
    AccountResponse createAccount(AccountRequest dto);
    AccountResponse getAccount(Long accountId);
    Map<String, BigDecimal> getTotalBalance(Long userId);
    List<AccountResponse> getAccountByUserId(Long userId);
}
