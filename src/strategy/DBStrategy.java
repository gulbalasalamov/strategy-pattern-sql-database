package strategy;
import constant.Constant;
import model.Account;
import service.DBAlgorithm;

import java.sql.*;


import static constant.Constant.*;

public class DBStrategy implements DBAlgorithm {

    /**
     * This method attempts to connect an application to a data source, which is specified by a database URL.
     * It also automatically creates a database "fileName.db" in project directory
     * Connecting to DMBS with the DriverManager class involves calling the method DriverManager.getConnection.
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html#drivermanager
     *
     * @param fileName
     * @return Connection object
     */
    @Override
    public Connection getConnection(String fileName) {
        String url = Constant.URL + fileName;
        try {
            // Typically, in the database URL, you also specify the name of an existing database to which you want to connect.
            // However, Constant.URL does not specify a specific database because here the method creates a new database.
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void createTable(Connection connection) {
        //Use executeUpdate method for INSERT, DELETE and UPDATE statements or for statements that return nothing, such as CREATE or DROP.
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_CREATE_TABLE); //Sql statement for creating a new table
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void dropTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(Constant.SQL_DROP_TABLE);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void addAccount(Connection connection, Account account) {

        //try (connection) {
        try {
            connection.setAutoCommit(false);
            Savepoint savepoint0 = connection.setSavepoint();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
                preparedStatement.setInt(1, account.getId());
                preparedStatement.setString(2, account.getCardNumber());
                preparedStatement.setString(3, account.getPin());
                preparedStatement.setInt(4, account.getBalance());
                preparedStatement.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(savepoint0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoking connection, retrieving requested data from table using SELECT statement with parameter markers
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
     *
     * @param connection
     * @param searchCriteria return Account object
     */
    @Override
    public Account loadAccount(Connection connection, String searchCriteria) {

        Account result = new Account(RESULT_OBJECT);
        //https://alvinalexander.com/blog/post/jdbc/jdbc-preparedstatement-select-like/ using prep
        //https://www.ibm.com/docs/en/db2/11.1?topic=applications-retrieving-data-from-tables-using-preparedstatementexecutequery-method

        //try (connection) {
        try {
            connection.setAutoCommit(false);
            //Savepoint savepoint0 = connection.setSavepoint();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ACCOUNT)) {
                preparedStatement.setString(2, searchCriteria); // Assign value to input parameter
                ResultSet resultSet = preparedStatement.executeQuery(); // Get the result table from the query
                while (resultSet.next()) { // position the cursor
                    result.setId(resultSet.getInt(1));//You can retrieve values using either the index number of the column or the alias or name of the column or the alias or name of the column.
                    result.setCardNumber(resultSet.getString(2)); //The column index is usually more efficient
                    result.setPin(resultSet.getString(3));
                    result.setBalance(resultSet.getInt(4));
                    connection.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                //connection.rollback(savepoint0); no need to rollback? as we aren't changing anything in records
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateAccount(Connection connection, String searchCriteria, int amount) {
        Account customer = loadAccount(connection, searchCriteria);
        //  TODO: implement method addBalance
        //String sqlUpdateQuery = "UPDATE card SET balance = balance " + amount + "WHERE NUMBER...;
        //try (connection) {
        try {
            connection.setAutoCommit(false);
            Savepoint savepoint0 = connection.setSavepoint();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
                preparedStatement.setInt(1, customer.getBalance() + amount);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(savepoint0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(Connection connection, String searchCriteria) {
        //TODO: implement method
        //try (connection) {
        try {
            connection.setAutoCommit(false);
            Savepoint savepoint0 = connection.setSavepoint();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT)) {
                preparedStatement.setString(2, searchCriteria);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(savepoint0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}