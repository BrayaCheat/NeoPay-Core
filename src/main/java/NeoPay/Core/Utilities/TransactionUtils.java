package NeoPay.Core.Utilities;

import NeoPay.Core.Models.Transaction;
import NeoPay.Core.Repositories.TransactionRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionUtils {

    private final TransactionRepository transactionRepository;

    public TransactionUtils(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public static String generateTransactionReferenceId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailedTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public static Specification<Transaction> sentOrReceived(Long accountId) {
        return (root, query, cb) ->
                cb.or(
                        cb.equal(root.get("sender").get("id"), accountId),
                        cb.equal(root.get("receiver").get("id"), accountId)
                );
    }

    public static Specification<Transaction> getReceiveTransactions(Long accountId) {
        return (root, query, cb) -> cb.equal(root.get("receiver").get("id"), accountId);
    }

    public static Specification<Transaction> getSenderTransactions(Long accountId) {
        return (root, query, cb) -> cb.equal(root.get("sender").get("id"), accountId);
    }

    public static Specification<Transaction> filterBySenderAccountNumber(String senderAccountNumber) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("sender").get("accountNumber")), "%" + senderAccountNumber.toLowerCase() + "%");
    }

    public static Specification<Transaction> filterByReceiverAccountNumber(String receiverAccountNumber) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("receiver").get("accountNumber")), "%" + receiverAccountNumber.toLowerCase() + "%");
    }

    public static Specification<Transaction> buildTransactionSearchSpec(
            Long accountId,
            boolean isReceiver,
            String senderAccountNumber,
            String receiverAccountNumber
    ) {
        Specification<Transaction> spec = isReceiver
                ? getReceiveTransactions(accountId)
                : getSenderTransactions(accountId);

        if (senderAccountNumber != null && !senderAccountNumber.isBlank()) {
            spec = spec.and(filterBySenderAccountNumber(senderAccountNumber));
        }

        if (receiverAccountNumber != null && !receiverAccountNumber.isBlank()) {
            spec = spec.and(filterByReceiverAccountNumber(receiverAccountNumber));
        }

        return spec;
    }


}
