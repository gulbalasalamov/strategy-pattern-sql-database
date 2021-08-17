package manager;


import context.BankContext;
import model.Account;
import model.Search;
import model.State;
import strategy.CardStrategy;
import strategy.DBStrategy;

import java.sql.Connection;
import java.util.Scanner;

public class BankingSystem {
    BankContext bankContext;
    Scanner scanner;
    public State state;
    Connection connection;

    public BankingSystem() {
        this.bankContext = new BankContext();
        this.scanner = new Scanner(System.in);
        this.state = State.MAIN;
    }

    /**
     * Initializes database. To avoid potential db issues, during each building existing database drops and creates a new one in the scope of the project.
     *
     * @param fileName
     */
    public void initializeDatabase(String fileName) {
        bankContext.setDbAlgorithm(new DBStrategy());
        connection = bankContext.getConnection(fileName);
        bankContext.dropTable(connection);
        bankContext.createTable(connection);
    }

    /**
     * Creates a new account with a randomly generated card number and PIN
     * then appends the new account to the accounts list
     */
    public void createAccount() {
        Account account = new Account();
        bankContext.setCardAlgorithm(new CardStrategy());
        String cardNumber = bankContext.generateValidCardNumber();
        String pin = bankContext.generatePin();
        account.setCardNumber(cardNumber);
        account.setPin(pin);
        //accounts.add(account);
        bankContext.addAccount(connection, account);
        System.out.println("\nYour card has been created\nYour card number:\n" + account.getCardNumber() + "\nYour card PIN:\n" + account.getPin() + "\n");
    }

    /**
     * Allows a user to login and perform further actions with their associated account if given parameters cardNumber and pin are valid
     * It calls displayAccountDetails() and passes corresponding account object
     */
    public void loginIntoAccount() {
        System.out.println("Enter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN: ");
        String pin = scanner.nextLine();

        Account customer = bankContext.checkLogin(connection, cardNumber, pin);
        if (customer != null) {
            System.out.println("You have successfully logged in!\n");
            state = State.LOGIN;
            displayAccountDetails(customer);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    /**
     * Prints the account menu and allows the user to choose from options
     *
     * @param account
     */
    public void displayAccountDetails(Account account) {
        while (state == State.LOGIN) {
            System.out.println("1. Balance \n2. Add income \n3. Do transfer \n4. Close account \n5. Log out \n0. Exit");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    Account temp = bankContext.loadAccount(connection, Search.BALANCE, account);
                    System.out.println("Balance: " + temp.getBalance() + "\n");
                    break;
                case "2":
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    addIncome(connection, income, account);
                    break;
                case "3":
                    //TODO: method
                    System.out.println("Transfer\nEnter card number:");
                    String cardNumber = scanner.next();
                    doTransfer(connection,account,cardNumber);
                    break;
                case "4":
                    closeAccount(connection, account);
                    break;
                case "5":
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

    public void doTransfer(Connection connection,Account sender, String recipient) {
         //else if

        if (!bankContext.checkLuhn(recipient)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else {
            if (!bankContext.doesAccountExist(connection,recipient)){
                System.out.println("Such a card does not exist.");
            } else {
                System.out.println("Enter how much money you want to transfer");
                int amount = scanner.nextInt();
                if (sender.getBalance()>=amount){
                    //TODO: do operation
                    bankContext.updateBalance(connection,-amount,sender);
                    System.out.println("Success!");
                } else {
                    System.out.println("Not enough money");
                }
            }
        }
    }

    /**
     * Closes the customer account and logs off. The main screen will show up.
     *
     * @param connection
     * @param customer
     */
    public void closeAccount(Connection connection, Account customer) {
        bankContext.deleteAccount(connection, customer);
        System.out.println("The account has been closed!");
        state = State.MAIN;
    }

    /**
     * Allow user to add money into their account
     *
     * @param connection
     * @param amount
     * @param account
     */
    public void addIncome(Connection connection, int amount, Account account) {
        bankContext.updateBalance(connection, amount, account);
        System.out.println("Income was added!\n");

    }

    /**
     * Allow user to log out and go to previous menu.
     */
    public void logout() {
        state = State.MAIN;
    }

    /**
     * It sets the system state EXIT and turns it off.
     */
    public void shutdown() {
        state = State.EXIT;
        System.out.println("\nBye!");
    }
}





