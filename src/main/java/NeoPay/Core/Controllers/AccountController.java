package NeoPay.Core.Controllers;

import NeoPay.Core.DTO.Request.AccountRequest;
import NeoPay.Core.DTO.Response.AccountResponse;
import NeoPay.Core.Services.ServiceImpl.AccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestParam(name = "userId") Long userId, @RequestBody AccountRequest dto){
        return ResponseEntity.status(201).body(accountService.createAccount(userId, dto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable(name = "accountId") Long accountId){
        return ResponseEntity.status(200).body(accountService.getAccount(accountId));
    }
}
