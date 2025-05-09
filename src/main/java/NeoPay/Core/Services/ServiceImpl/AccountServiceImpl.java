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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public AccountResponse createAccount(AccountRequest dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("User id: " + dto.getUserId() + " not found!"));
//        if(user.getAccounts().toArray().length == 2){
//            throw new RuntimeException("A user can only have 2 accounts!");
//        }
        if(user.getAccounts().stream().filter(account -> !account.isDelete()).toArray().length >= 2){
            throw new RuntimeException("A user can only have 2 accounts!");
        }
        Account account = Account.builder()
                .accountName(dto.getAccountName())
                .accountType(dto.getAccountType())
                .accountNumber(AccountUtils.generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .currency("USD")
                .active(true)
                .isDelete(false)
                .user(user)
                .build();
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Override
    public AccountResponse getAccount(Long accountId) {
        return accountMapper.toDTO(accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account id: " + accountId + " not found!")));
    }

    @Override
    public Map<String, BigDecimal> getTotalBalance(Long userId) {
        BigDecimal total = accountRepository.findTotalBalanceByUserId(userId);
        total = total != null ? total : BigDecimal.ZERO;
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("totalBalance", total);
        return response;
    }

    @Override
    public List<AccountResponse> getAccountByUserId(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new NotFoundException("User id: " + userId + "not found!");
        }
        return accountRepository.findByUserId(userId)
                .stream()
                .filter(account -> !account.isDelete())
                .map(accountMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account with id: " + accountId + " not found."));
        if(account.getBalance().compareTo(BigDecimal.ZERO) > 0){
            throw new RuntimeException("Cannot delete account. Account still has a balance!");
        }
        account.setActive(false);
        account.setDelete(true);
        account.setDeletedAt(LocalDateTime.now());
        accountRepository.save(account);
    }
}
