package manager;

import context.BankContext;
import model.Account;
import model.State;
import strategy.CreditCardStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {

    //TODO: implement context here, CommandLineUI
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


//    public void start(String command) {
//        while (state != State.EXIT) {
//            switch (state) {
//                case MAIN:
//                    switch (command) {
//                        case "1":
//                            createAccount();
//                            break;
//                        case "2":
//                            loginIntoAccount();
//                            break;
//                        default:
//                            break;
//                    }
//                    break;
//                case LOGIN:
//                    switch (command) {
//                        case "1":
//                            displayAccountDetails();
//                            break;
//                        case "2":
//                            logout();
//                            break;
//                        default:
//                            break;
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//        printMenu();
//    }
//
//    public void printMenu() {
//        System.out.println();
//        switch (state) {
//            case MAIN:
//                System.out.println("1. Create an account");
//                System.out.println("2. Log into account");
//                System.out.println("0. Exit");
//                break;
//            case LOGIN:
//                System.out.println("1. Balance");
//                System.out.println("2. Log out");
//                System.out.println("3. Exit");
//                break;
//            default:
//                break;
//        }
//    }

    public void createAccount() {
        Account account = new Account();
        bankContext.setCardAlgorithm(new CreditCardStrategy());
        String cardNumber = bankContext.generateCardNumber();
        String pin = bankContext.generatePin();
        account.setCardNumber(cardNumber);
        account.setPin(pin);
        accounts.add(account);
        //accountData.put(account.getCardNumber(),account.getPin());
        System.out.println("\nYour card has been created\nYour card number: " + account.getCardNumber() + "\nYour card PIN: " + account.getPin());
    }

    public void loginIntoAccount() {
        System.out.println("Enter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN: ");
        String pin = scanner.nextLine();

        Account found = searchForAccount(cardNumber, pin);
        if (found != null) {
            System.out.println("You have successfully logged in!\n");
            displayAccountDetails(found);
            state = State.LOGIN;
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
        while (state==State.LOGIN) {
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
    public void shutdown(){
        state = State.EXIT;
        System.out.println("\nBye!");
    }
    //TODO: write method printMenu() - done
    //TODO: write method createAccount - done
    //TODO: write method loginToAccount
    //TODO: write method searchForAccount
    //TODO: write method displayAccountDetails
    //TODO: write method checkState - isOnline, powerOff
    //TODO: replace boolean with enums
}
