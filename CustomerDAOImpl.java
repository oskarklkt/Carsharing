package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        String sql = "INSERT INTO CUSTOMER(ID, NAME, RENTED_CAR_ID) VALUES(?,?, null)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setString(2, customer.getName());


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
        Connection connection = Database.getConnection();

        String sql = "DELETE FROM CUSTOMER WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        return preparedStatement.executeUpdate();
    }


    @Override
    public int delete(int customerId) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "DELETE FROM CUSTOMER WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerId);
        return preparedStatement.executeUpdate();
    }

    @Override
    public int getId() throws SQLException, ClassNotFoundException {
        return getAll().size() + 1;
    }

    @Override
    public void rentACar(int customerId) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Customer customer = getAll().get(customerId - 1);
        CompanyDAOImpl companyDAOImpl = new CompanyDAOImpl();
        CarDAOImpl carDAOImpl = new CarDAOImpl();

        if (!(getRentedCarId(customerId) == 0)) {
            System.out.println("You've already rented a car!");
            CustomerInterface.start(customerId);
        } else if (companyDAOImpl.getAll().isEmpty()) {
            System.out.println("The company list is empty!");
            CustomerInterface.start(customerId);

        } else {
            System.out.println("Choose a company:");
            companyDAOImpl.getAll().stream().forEach(Company::toString);
            System.out.println("0. Back");
            int companyId = scanner.nextInt();
            if (companyId == 0) {
                CustomerInterface.start(customerId);
            }
            List<Car> allCarsOfCompany = companyDAOImpl.getAllCarsOfCompany(companyId);
            for (Car car : allCarsOfCompany) {
                if (carDAOImpl.isCarRented(car.getId())) {
                    allCarsOfCompany.remove(car);
                }
            }
            System.out.println("Choose a car:");
            int counter = 1;
            for (Car car : allCarsOfCompany) {
                System.out.println(counter + ". " + car.getName());
                counter++;
            }
            System.out.println("0. Back");
            counter = 1;
            int chosenCar = scanner.nextInt();
            if (chosenCar == 0) {
                CustomerInterface.start(customerId);
            }
            System.out.println();
            System.out.println("You rented " + "'" + allCarsOfCompany.get(chosenCar - 1).getName() + "'");
            System.out.println();
            Car car = allCarsOfCompany.get(chosenCar - 1);
            setRentCarId(customerId, car.getId());
            CustomerInterface.start(customerId);
        }
    }

    @Override
    public void returnCar(int customerId) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();
        CarDAOImpl carDAOImpl = new CarDAOImpl();

        String sql =    "SELECT * " +
                        "FROM CAR " +
                        "JOIN CUSTOMER ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
                        "WHERE CUSTOMER.ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int compId = resultSet.getInt("COMPANY_ID");

            //carDAOImpl.insert(new Car(carDAOImpl.getId(), name, compId));
        } else {
            System.out.println("You didn't rent a car!");
            System.out.println();
            CustomerInterface.start(customerId);
        }

        Customer customer = get(customerId);
        delete(customerId);
        insert(new Customer(customerId, customer.getName(), null));
        System.out.println("You've returned a rented car!");
        connection.close();
        preparedStatement.close();
        CustomerInterface.start(customerId);
    }

    @Override
    public void showRentedCar(int customerId) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "SELECT CAR.NAME, COMPANY.NAME " +
                "FROM CAR " +
                "JOIN COMPANY ON COMPANY.ID = CAR.COMPANY_ID " +
                "JOIN CUSTOMER ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
                "WHERE CUSTOMER.ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println();
            System.out.println("You didn't rent a car!");
            System.out.println();
            CustomerInterface.start(customerId);
        } else {
            String carName = resultSet.getString(1);
            String companyName = resultSet.getString(2);
            System.out.println();
            System.out.println("Your rented car:");
            System.out.println(carName);
            System.out.println("Company:");
            System.out.println(companyName);
            System.out.println();
            CustomerInterface.start(customerId);
        }
    }

    @Override
    public Integer getRentedCarId(int customerId) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerId);

        ResultSet resultSet = preparedStatement.executeQuery();
        Integer result = null;

        while (resultSet.next()) {
            result = resultSet.getInt(1);
            if (result.toString().equals("'NULL'")) {
                result = 0;
            }
        }
        return result;
    }


    @Override
    public void setRentCarId(int id, int carId) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, carId);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        connection.close();
    }



}
