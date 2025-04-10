package NeoPay.Core.Utilities;

import NeoPay.Core.Models.Transaction;
import NeoPay.Core.Repositories.TransactionRepository;
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

    public static String generateTransactionReferenceId(){
        return "TXN-" + UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailedTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
