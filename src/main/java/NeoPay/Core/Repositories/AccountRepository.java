package NeoPay.Core.Repositories;

import NeoPay.Core.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    @Query(value = "select sum(balance) from accounts where user_id = :userId", nativeQuery = true)
    BigDecimal findTotalBalanceByUserId(@Param("userId") Long userId);

    @Query(value = "select * from accounts where user_id = :userId", nativeQuery = true)
    List<Account> findByUserId(@Param("userId") Long userId);
}
