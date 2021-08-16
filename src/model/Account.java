package model;

/**
 * Represents a user's bank account
 */
public class Account {
    static int ID = 0;
    int id;
    String cardNumber;
    String pin;
    int balance = 0;
    //BigDecimal balance = new BigDecimal(0);

    public Account() {
        ID++;
        this.id = ID;
    }

    public Account(int id){
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
