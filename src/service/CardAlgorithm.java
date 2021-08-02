package service;

/**
 * The interface that strategy classes implement
 */
public interface CardAlgorithm {
    String generateCardNumber();
    String generatePin();
}
