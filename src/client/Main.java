package client;

import manager.BankingSystem;
import model.State;

import java.util.Scanner;

public class Main {
    /**
     * Prints the main menu and allows user to select from options for further operations
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem();
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
