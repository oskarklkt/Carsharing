package carsharing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database.dropTables();
        Database.createTables();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        Menu.start();

        connection.close();
    }
}