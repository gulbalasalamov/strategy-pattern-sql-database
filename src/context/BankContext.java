package context;

import model.Account;
import service.CardAlgorithm;
import service.DBAlgorithm;

import java.sql.Connection;

/**
 * A special class for storing reference to a strategy
 * The context delegates execution to an instance of concrete strategy through its interface instead of implementing behaviour itself
 */
public class BankContext {
    CardAlgorithm cardAlgorithm;
    DBAlgorithm dbAlgorithm;

    public void setCardAlgorithm(CardAlgorithm cardAlgorithm) {
        this.cardAlgorithm = cardAlgorithm;
    }

    public void setDbAlgorithm(DBAlgorithm dbAlgorithm) {
        this.dbAlgorithm = dbAlgorithm;
    }

    public String generateValidCardNumber() {
        return this.cardAlgorithm.generateValidCardNumber();
    }

    public String generatePin() {
        return this.cardAlgorithm.generatePin();
    }

    public Connection getConnection(String fileName) {
        return this.dbAlgorithm.getConnection(fileName);
    }

    public void createTable(Connection connection) {
        this.dbAlgorithm.createTable(connection);
    }

    public void dropTable(Connection connection) {
        this.dbAlgorithm.dropTable(connection);
    }

    public void addAccount(Connection connection, Account account) {
        this.dbAlgorithm.addAccount(connection, account);
    }

    public void updateAccount(Connection connection, String searchCriteria, int amount) {
        this.dbAlgorithm.updateAccount(connection, searchCriteria, amount);
    }

    public Account loadAccount(Connection connection, String searchCriteria) {
        return this.dbAlgorithm.loadAccount(connection, searchCriteria);
    }

    public void deleteAccount(Connection connection, String searchCriteria){
        dbAlgorithm.deleteAccount(connection,searchCriteria);
    }
}
