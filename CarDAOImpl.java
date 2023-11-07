package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    @Override
    public Car get(int id) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        Car car = null;

        String sql = "SELECT * FROM CAR WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int oid = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int fkid = resultSet.getInt("COMPANY_ID");

            car = new Car(oid, name, fkid);
        }
        connection.close();
        preparedStatement.close();

        return car;
    }

    @Override
    public List<Car> getAll() throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        Car car = null;
        List<Car> carList = new ArrayList<>();

        String sql = "SELECT * FROM CAR";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int fkid = resultSet.getInt("COMPANY_ID");

            carList.add(new Car(id, name, fkid));
        }
        connection.close();

        return carList;
    }

    @Override
    public int save(Car car) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int insert(Car car) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "INSERT INTO CAR (ID, NAME, COMPANY_ID) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, car.getId());
        preparedStatement.setString(2, car.getName());
        preparedStatement.setInt(3, car.getCompany_id());

        int result = preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public int update(Car car) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int delete(Car car) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "DELETE FROM CAR WHERE NAME = /'?/'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, car.getName());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int getId() throws SQLException, ClassNotFoundException {
        return getAll().size() + 1;
    }

    public List<Car> getCarListByCompanyId (int companyId) throws SQLException, ClassNotFoundException {
        List<Car> allCars = getAll();
        List<Car> companyCars = new ArrayList<>();
        for (Car car : allCars) {
            if (car.getCompany_id() == companyId) {
                companyCars.add(car);
            }
        }
        return companyCars;
    }

}
