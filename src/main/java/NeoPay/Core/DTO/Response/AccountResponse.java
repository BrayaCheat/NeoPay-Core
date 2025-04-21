package NeoPay.Core.DTO.Response;

import NeoPay.Core.Models.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountName;
    private AccountType accountType;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private boolean active;
    private boolean isDelete;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deactivatedAt;
    private LocalDateTime deletedAt;

    public enum AccountType {
        SAVING,
        SPENDING,
        BUSINESS
    }
}



