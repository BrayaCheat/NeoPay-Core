package NeoPay.Core.Utilities;

import java.util.UUID;

public class TransactionUtils {

    public static String generateTransactionReferenceId(){
        return "TXN-" + UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();
    }
}
