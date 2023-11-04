package carsharing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database.createCompanyTable();
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();


        ResultSet rs = statement.executeQuery("SELECT * FROM COMPANY");

        while (rs.next()) {
            System.out.println(rs.getString(2));
        }
    }
}