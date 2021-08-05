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
        String cardNumber = bankContext.generateCardNumber();
        cardNumber = checkSum(cardNumber);
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
    }

    //TODO: Create luhnCheck() method to check digit to verify that the card number is valid
    //The Luhn algorithm is used to validate a credit card number
    /*public void luhnCheck(String cardNumber) {
        //cardNumber is 16 digits and starting index 0, ending 15.
        int sumOfOdds = 0;
        int sumOfEvens = 0;
        for (int i = 1; i < cardNumber.length(); i += 2) {
            int temp = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            sumOfOdds += temp;
        }

        for (int i = 0; i < cardNumber.length(); i += 2) {
            int temp = 2 * Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            if (temp > 9) {
                temp -= 9;
            }
            sumOfEvens += temp;
        }
    }*/

    /*
    It check digits by Luhn algorithm to verify that the card number is valid.
    If no, in order for final card number to pass the validity check, last digit is modified.
     */
    public String checkSum(String cardNumber) {
        StringBuilder stringBuilder = new StringBuilder(cardNumber);
        int sum = 0;
        List<Integer> numbers = new ArrayList<>();
        //Step1: drop the last digit
        //Step2: multiply even indexes by 2
        for (int i = 0; i < stringBuilder.length() - 1; i++) {
            int temp = 0;
            if (i % 2 == 0) {
                temp = 2 * Integer.parseInt(String.valueOf(stringBuilder.charAt(i)));
                //Step3: Subtract 9 to numbers over 9
                if (temp >= 10) {
                    temp -= 9;
                }
            } else {
                temp = Integer.parseInt(String.valueOf(stringBuilder.charAt(i)));
            }
            numbers.add(temp);
        }
        // Add all numbers
        numbers.add(Integer.parseInt(String.valueOf(stringBuilder.charAt(15))));
        for (int i = 0; i < numbers.size(); i++) {
            sum += numbers.get(i);
        }

        if (sum % 10 == 0) {
            return stringBuilder.toString();
        } else {
//            int checkSum = 10 - (sum % 10);
//            String test = "";
//            test = stringBuilder.deleteCharAt(15).append(checkSum).toString();
//            String test2 = "";
//            test2 = stringBuilder.replace(15, 15, String.valueOf(stringBuilder.charAt(15))).toString();
//            return test + " " + test2;
            return stringBuilder.replace(15, 15, String.valueOf(stringBuilder.charAt(15))).toString();
        }
    }
}
