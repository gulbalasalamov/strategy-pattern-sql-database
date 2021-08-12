package constant;

public class Constant {
    public static final String BANK_IDENTIFICATION_NUMBER = "400000";
    public static final String URL = "jdbc:sqlite:"; //String url = "jdbc:sqlite:" + args[1]; // // SQLite connection string for creating DB
    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS card (\n"
            + "id INTEGER PRIMARY KEY,\n"
            + "number TEXT,\n"
            + "pin TEXT,\n"
            + "balance INTEGER DEFAULT 0"
            + ");";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS card";
    public static final String SQL_INSERT = "INSERT INTO card (id,number,pin,balance) VALUES (?,?,?,?)";

}
