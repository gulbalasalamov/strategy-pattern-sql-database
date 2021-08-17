package service;

import model.Account;
import model.Search;

import java.sql.Connection;


/**
 * The interface that strategy class implement
 */
public interface DBAlgorithm {
    Connection getConnection(String fileName);

    void createTable(Connection connection);

    void dropTable(Connection connection);

    void addAccount(Connection connection, Account account);

    Account checkLogin(Connection connection, String number, String pin);

    void updateBalance(Connection connection,int amount,Account account);

    Account loadAccount(Connection connection, Search searchCriteria, Account account);

    boolean doesAccountExist(Connection connection, String cardNumber);

    void deleteAccount(Connection connection,Account account);
}
