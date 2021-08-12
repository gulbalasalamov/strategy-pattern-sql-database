package model;

import java.math.BigDecimal;

/**
 * Represents a user's bank account
 */
public class Account {
    static int ID = 0;
    int id = 0;
    String cardNumber;
    String pin;
    BigDecimal balance = new BigDecimal(0);

    public Account() {
        ID++;
        this.id = ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
