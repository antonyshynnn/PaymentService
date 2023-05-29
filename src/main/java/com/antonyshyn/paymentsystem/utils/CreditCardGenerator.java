package com.antonyshyn.paymentsystem.utils;

import java.util.Calendar;
import java.util.Random;

public class CreditCardGenerator {

    public static String generateRandomCreditCardNumber() {
        Random random = new Random();

        StringBuilder creditCardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            creditCardNumber.append(digit);
        }

        return creditCardNumber.toString();
    }

    public static String generateRandomCVV() {
        Random random = new Random();

        int cvv = random.nextInt(900) + 100; // Generate a random 3-digit number between 100 and 999
        return String.valueOf(cvv);
    }

    public static String generateRandomExpirationDate() {
        Random random = new Random();

        // Generate a random month (1-12)
        int month = random.nextInt(12) + 1;

        // Generate a random year within the next 10 years
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int year = currentYear + random.nextInt(10);

        // Format the month and year as "MM/YY"
        return String.format("%02d/%02d", month, year % 100);
    }
}
