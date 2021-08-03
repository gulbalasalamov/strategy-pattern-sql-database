package strategy;

import service.CardAlgorithm;

import java.util.Random;

public class CreditCardStrategy implements CardAlgorithm {
    Random random = new Random();

    @Override
    public String generateCardNumber() {
        long lowerBound = 1_000_000_000L;
        long upperBound = 9_000_000_000L;
        long accountNumber = (long) (Math.random() * (upperBound - lowerBound)) + lowerBound;
        return "400000" + accountNumber;
    }

    @Override
    public String generatePin() {
        int lowerBound = 1000;
        int upperBound = 9000;
        int pin = random.nextInt(upperBound - lowerBound) + lowerBound;
        return String.valueOf(pin);
    }
}
