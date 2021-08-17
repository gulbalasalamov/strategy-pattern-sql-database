package service;

/**
 * The interface that strategy class implement
 */
public interface CardAlgorithm {
    String generateValidCardNumber();
    String generatePin();
    boolean checkLuhn(String cardNumber);
}
