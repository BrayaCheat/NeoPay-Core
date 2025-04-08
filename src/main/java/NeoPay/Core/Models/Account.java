package NeoPay.Core.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private String accountNumber;

    @Column()
    private BigDecimal balance;

    @Column(length = 3, nullable = false)
    private String currency;

    @Column()
    private boolean isActive = true;

    @Column()
    private boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Transaction> receivedTransactions;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deactivate_at")
    private LocalDateTime deactivatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum AccountType {
        SAVING,
        SPEDNING,
        BUSINESS
    }
}
