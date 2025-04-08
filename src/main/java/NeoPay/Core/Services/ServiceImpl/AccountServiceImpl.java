package NeoPay.Core.Services.ServiceImpl;

import NeoPay.Core.DTO.Mapper.AccountMapper;
import NeoPay.Core.DTO.Request.AccountRequest;
import NeoPay.Core.DTO.Response.AccountResponse;
import NeoPay.Core.Exceptions.NotFoundException;
import NeoPay.Core.Models.Account;
import NeoPay.Core.Models.User;
import NeoPay.Core.Repositories.AccountRepository;
import NeoPay.Core.Repositories.UserRepository;
import NeoPay.Core.Services.AccountService;
import NeoPay.Core.Utilities.AccountUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountResponse createAccount(Long userId, AccountRequest dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id: " + userId + " not found!"));
        if(user.getAccounts().toArray().length == 2){
            throw new RuntimeException("A user can only have 2 accounts!");
        }
        Account account = Account.builder()
                .accountName(dto.getAccountName())
                .accountType(dto.getAccountType())
                .accountNumber(AccountUtils.generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .currency("USD")
                .isActive(true)
                .isDelete(false)
                .user(user)
                .build();
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Override
    public AccountResponse getAccount(Long accountId) {
        return accountMapper.toDTO(accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account id: " + accountId + " not found!")));
    }
}
