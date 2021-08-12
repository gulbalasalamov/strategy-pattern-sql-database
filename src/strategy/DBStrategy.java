package strategy;

import constant.Constant;
import model.Account;
import org.sqlite.SQLiteDataSource;
import service.DBAlgorithm;

import java.sql.*;

import static constant.Constant.SQL_CREATE_TABLE;
import static constant.Constant.SQL_INSERT;

public class DBStrategy implements DBAlgorithm {
    String sql = "SELECT * FROM tableName";
    //Connection connection = null;

    /**
     * This method attempts to connect an application to a data source, which is specified by a database URL.
     * It also automatically creates a database "fileName.db" in project directory
     * Connecting to DMBS with the DriverManager class involves calling the method DriverManager.getConnection.
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html#drivermanager
     * @param fileName
     * @return Connection object
     */
    @Override
    public Connection getConnection(String fileName) {
        String url = Constant.URL + fileName;
        try {
            // Typically, in the database URL, you also specify the name of an existing database to which you want to connect.
            // However, Constant.URL does not specify a specific database because here the method creates a new database.
            Connection connection = DriverManager.getConnection(url);
            return connection;
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
            //A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.
            PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT);
            //TODO: figure out id
            prepareStatement.setInt(1, account.getId());
            prepareStatement.setString(2, account.getCardNumber());
            prepareStatement.setString(3, account.getPin());
            prepareStatement.setInt(4, Integer.parseInt(String.valueOf(account.getBalance())));

            prepareStatement.execute();
            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAccount(Connection connection, Account account) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql); //The ResultSet object represents a table that contains records from the database result set.
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String pin = resultSet.getString("pin");
                int balance = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Connection connection, Account account) {
        //TODO: implement
    }
}
