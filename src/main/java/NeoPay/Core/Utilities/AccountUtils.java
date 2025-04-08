package NeoPay.Core.Utilities;

import java.util.Random;

public class AccountUtils {

    private static final Random random = new Random();

    public static String generateAccountNumber() {
        // Generate a number between 100000000 and 999999999
        int accountNumber = 100_000_000 + random.nextInt(900_000_000);
        return String.valueOf(accountNumber);
    }
}

