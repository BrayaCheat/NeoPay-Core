package NeoPay.Core.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String accountName;
    private String accountNumber;
    private String currency;
    private BigDecimal balance;
    private String accountType;
    private boolean isActive;
    private String createdAt;
}

