package context;

import service.CardAlgorithm;

/**
 * A special class for storing reference to a strategy
 * The context delegates execution to an instance of concrete strategy through its interface instead of implementing behaviour itself
 */
public class BankContext {
    CardAlgorithm cardAlgorithm;

    public void setCardAlgorithm(CardAlgorithm cardAlgorithm) {
        this.cardAlgorithm = cardAlgorithm;
    }

    String generateCardNumber(){
        return this.cardAlgorithm.generateCardNumber();
    }

    String generatePin(){
        return this.cardAlgorithm.generatePin();
    }
}
