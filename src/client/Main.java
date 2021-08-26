package client;

import manager.BankingSystem;
import model.State;

import java.util.Scanner;

public class Main {
    /**
     * Prints the main menu and allows user to select from options for further operations
     * It reads the database file name from the command line argument.
     * Filename should be passed to the program using -fileName argument, for example, -fileName db.s3db
     * @param args
     */
    public static void main(String[] args) {

        String fileName = "";
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-fileName":
                    fileName = args[i + 1];
                    break;
            }
        }

        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem(fileName);

        while (bankingSystem.state != State.EXIT) {
            System.out.println("1. Create an account\n2. Log into account\n0. Exit");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    bankingSystem.createAccount();
                    break;
                case "2":
                    bankingSystem.loginIntoAccount();
                    break;
                case "0":
                    bankingSystem.shutdown();
                    break;
                default:
                    System.out.println("Unknown input");
            }
        }
    }
}
