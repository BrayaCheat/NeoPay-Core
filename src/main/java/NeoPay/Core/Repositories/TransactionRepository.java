package NeoPay.Core.Repositories;


import NeoPay.Core.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    @Query(value = "select * from transactions where sender_id = :accountId or receiver_id = :accountId", nativeQuery = true)
    List<Transaction> getTransaction(@Param("accountId") Long accountId);

    @Query(value = "select * from transactions where receive_id = :accountId", nativeQuery = true)
    List<Transaction> getReceiveTransaction(@Param("accountId") Long accountId);

    @Query(value = "select * from transactions where sender_id = :accountId", nativeQuery = true)
    List<Transaction> getTransferTransaction(@Param("accountId") Long accountId);

    @Query(value = "select count(id) from transactions where receiver_id = :accountId or sender_id = :accountId", nativeQuery = true)
    Long transactionCount(@Param("accountId") Long accountId);
}
