package NeoPay.Core.Controllers;

import NeoPay.Core.DTO.Request.AccountRequest;
import NeoPay.Core.DTO.Response.AccountResponse;
import NeoPay.Core.Models.Account;
import NeoPay.Core.Services.ServiceImpl.AccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest dto){
        return ResponseEntity.status(201).body(accountService.createAccount(dto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable(name = "accountId") Long accountId){
        return ResponseEntity.status(200).body(accountService.getAccount(accountId));
    }

    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<Map<String, BigDecimal>> getTotalBalance(@PathVariable(name = "userId") Long userId){
        return ResponseEntity.status(200).body(accountService.getTotalBalance(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountByUserId(@PathVariable(name = "userId") Long userId){
        return ResponseEntity.status(200).body(accountService.getAccountByUserId(userId));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable(name = "accountId") Long accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.status(200).body("Deleted account id: " + accountId);
    }
}
