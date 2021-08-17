package strategy;

import constant.Constant;
import model.Account;
import model.Search;
import service.DBAlgorithm;

import java.sql.*;
import java.util.Locale;

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

    public Account checkLogin(Connection connection, String number, String pin) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_LOGIN)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account customer = new Account(RESULT_OBJECT_ID);
            while (resultSet.next()) {
                customer.setId(resultSet.getInt(1)); // TODO: verify result object id replaced by real customer id?
                customer.setCardNumber(resultSet.getString(2));
                customer.setPin(resultSet.getString(3));
                customer.setBalance(resultSet.getInt(4));
                if (number.equals(customer.getCardNumber()) && pin.equals(customer.getPin())) {
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Invoking connection, retrieving requested data from table using SELECT statement with parameter markers
     * Useful resources:
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
     * http://tutorials.jenkov.com/jdbc/preparedstatement.html
     * //https://www.ibm.com/docs/en/db2/11.1?topic=applications-retrieving-data-from-tables-using-preparedstatementexecutequery-method
     *
     * @param connection return Account object
     */
    @Override
    public Account loadAccount(Connection connection, Search searchCriteria,Account customer) {
        //Account result = new Account(RESULT_OBJECT_ID);
        String search = searchCriteria.toString().toLowerCase();
        switch (search) {
            case "balance":
                //TODO: method
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ACCOUNT);
                    preparedStatement.setString(1, customer.getCardNumber());
                    //preparedStatement.setString(2, searchCriteria); // Assign value to input parameter
                    ResultSet resultSet = preparedStatement.executeQuery(); // Get the result table from the query
                    while (resultSet.next()) { // position the cursor
                        customer.setId(resultSet.getInt(1));//You can retrieve values using either the index number of the column or the alias or name of the column or the alias or name of the column.
                        customer.setCardNumber(resultSet.getString(2)); //The column index is usually more efficient
                        customer.setPin(resultSet.getString(3));
                        customer.setBalance(resultSet.getInt(4));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return customer;
            default:
                break;
        }
        return null;
    }

    /**
     * ref: https://www.ibm.com/docs/en/db2/11.1?topic=sql-updating-data-in-tables-using-preparedstatementexecuteupdate-method
     *
     * @param connection
     * @param amount
     */
    @Override
    public void updateBalance(Connection connection, int amount,Account customer) {

        try {
            connection.setAutoCommit(false);
            Savepoint savepoint0 = connection.setSavepoint();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BALANCE)) {
                preparedStatement.setInt(1, customer.getBalance() + amount);
                preparedStatement.setString(2, customer.getCardNumber());
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
    public void deleteAccount(Connection connection,Account customer) {
        try {
            connection.setAutoCommit(false);
            Savepoint savepoint0 = connection.setSavepoint();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT)) {
                preparedStatement.setString(1, customer.getCardNumber());
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