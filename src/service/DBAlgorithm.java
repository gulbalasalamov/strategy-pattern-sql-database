package service;

import model.Account;
import java.sql.Connection;

/**
 * The interface that strategy class implement
 */
public interface DBAlgorithm {
    Connection getConnection(String fileName);
    void createTable(Connection connection);
    void dropTable(Connection connection);
    void addAccount(Connection connection,Account account);
    void loadAccount(Connection connection,Account account);
    void updateAccount(Connection connection,Account account);
}
