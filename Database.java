package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static String URL = "jdbc:h2:./src/carsharing/db/carsharing";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName ("org.h2.Driver");
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(true);
        return connection;
    }

    public static void createCompanyTable() throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY (ID INT, NAME VARCHAR);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    public static void dropCompanyTable() throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        String sql = "DROP TABLE IF EXISTS COMPANY";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
}
