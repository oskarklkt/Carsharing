package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public Customer get(int id) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        Customer customer = null;

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int oid = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int fkid = resultSet.getInt("RENTED_CAR_ID");

            customer = new Customer(oid, name, fkid);
        }
        connection.close();
        preparedStatement.close();
        return customer;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        Customer customer = null;
        List<Customer> customerList = new ArrayList<>();

        String sql = "SELECT * FROM CUSTOMER";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int fkid = resultSet.getInt("RENTED_CAR_ID");

            customerList.add(new Customer(id, name, fkid));
        }
        connection.close();

        return customerList;
    }

    @Override
    public int save(Customer customer) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int insert(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "INSERT INTO CUSTOMER(ID, NAME, RENTED_CAR_ID) VALUES(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setInt(3, customer.getRented_car_id());

        int result = preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public int update(Customer customer) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int delete(Customer customer) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int getId() throws SQLException, ClassNotFoundException {
        return getAll().size() + 1;
    }
}
