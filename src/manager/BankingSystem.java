package manager;

import context.BankContext;
import model.Account;
import model.State;
import strategy.CreditCardStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {

    BankContext bankContext;
    Scanner scanner;
    //HashMap<String, String> accountData; // (cardNumber,pin)
    List<Account> accounts;
    public State state;

    public BankingSystem() {
        this.bankContext = new BankContext();
        this.scanner = new Scanner(System.in);
        //this.accountData = new HashMap<>();
        this.accounts = new ArrayList<>();
        this.state = State.MAIN;
    }

    public void createAccount() {
        Account account = new Account();
        bankContext.setCardAlgorithm(new CreditCardStrategy());
        String cardNumber = bankContext.generateValidCardNumber();
        String pin = bankContext.generatePin();
        account.setCardNumber(cardNumber);
        account.setPin(pin);
        accounts.add(account);
        //accountData.put(account.getCardNumber(),account.getPin());
        System.out.println("\nYour card has been created\nYour card number:\n" + account.getCardNumber() + "\nYour card PIN:\n" + account.getPin() + "\n");
    }

    public void loginIntoAccount() {
        System.out.println("Enter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN: ");
        String pin = scanner.nextLine();

        Account found = searchForAccount(cardNumber, pin);
        if (found != null) {
            System.out.println("You have successfully logged in!\n");
            state = State.LOGIN;
            displayAccountDetails(found);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    public Account searchForAccount(String cardNumber, String pin) {
        for (Account account : accounts) {
            if (cardNumber.equals(account.getCardNumber()) && pin.equals(account.getPin())) {
                return account;
            }
        }
        return null;
    }

    public void displayAccountDetails(Account account) {
        while (state == State.LOGIN) {
            System.out.println("1. Balance\n2. Log out\n0. Exit");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    System.out.println("\nBalance: " + account.getBalance());
                    break;
                case "2":
                    System.out.println("");
                    logout();
                    break;
                case "0":
                    shutdown();
                    break;
                default:
                    System.out.println("Unknown choice");
                    break;
            }
        }

    }

    public void logout() {
        state = State.MAIN;
    }

    public void shutdown() {
        state = State.EXIT;
        System.out.println("\nBye!");
    }}

    /*
    It check digits by Luhn algorithm to verify that the card number is valid.
    If no, in order for final card number to pass the validity check, last digit is modified.
     */



