package service;

/**
 * The interface that strategy classes implement
 */
public interface CardAlgorithm {
    String generateValidCardNumber();
    String generatePin();
}
