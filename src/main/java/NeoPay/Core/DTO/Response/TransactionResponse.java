package NeoPay.Core.DTO.Response;

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
public class TransactionResponse {
    private Long id;
    private String reference;

    private String sender;
    private String receiver;

    private BigDecimal amount;
    private String currency;

    private String remark;
    private String type;    // e.g. "TRANSFER", "DEPOSIT"
    private String status;  // e.g. "SUCCESS", "FAILED"

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

