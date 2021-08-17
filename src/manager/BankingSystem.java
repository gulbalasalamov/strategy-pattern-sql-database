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
    //HashMap<String, String> accountData; // (cardNumber,pin)
    //List<Account> accounts;
    public State state;
    Connection connection;

    public BankingSystem() {
        this.bankContext = new BankContext();
        this.scanner = new Scanner(System.in);
        //this.accountData = new HashMap<>();
        //this.accounts = new ArrayList<>();
        this.state = State.MAIN;
    }

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
     * Allows a user to login and perform actions with their account
     */
    public void loginIntoAccount() {
        System.out.println("Enter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN: ");
        String pin = scanner.nextLine();

        //Account found = searchForAccount(cardNumber, pin);
        Account customer = bankContext.checkLogin(connection, cardNumber, pin);
        //TODO: implement sql login
        if (customer != null) {
            System.out.println("You have successfully logged in!\n");
            state = State.LOGIN;
            displayAccountDetails(customer);
            //bankContext.deleteAccount(connection,);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    /**
     * It checks whether corresponding account exists by given parameters cardNumber and pin
     *
     * @param cardNumber
     * @param pin
     * @return
     */
//    public Account searchForAccount(String cardNumber, String pin) {
//        //TODO: fix. This method will implement sql.
//        Account found = bankContext.checkLogin(connection,cardNumber,pin);
//        if (cardNumber.equals(found.getCardNumber()) && pin.equals(found.getPin())) {
//            return found;
//        }
//        return null;
//    }

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
                    //System.out.println("\nBalance: " + account.getBalance());
                    Account temp = bankContext.loadAccount(connection, Search.BALANCE, account);
                    System.out.println(temp.getBalance());
                    break;
                case "2":
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    addIncome(connection, income, account);
                    break;
                case "3":
                    //TODO: method
                    //doTransfer();
                    break;
                case "4":
                    //TODO: method
                    closeAccount(connection,account);
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

    public void closeAccount(Connection connection, Account customer) {
        bankContext.deleteAccount(connection, customer);
        state = State.MAIN;
    }

    public void addIncome(Connection connection, int amount, Account account) {
        bankContext.updateBalance(connection, amount, account);
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





