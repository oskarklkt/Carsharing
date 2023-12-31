package carsharing;

import java.sql.SQLException;

public interface CustomerDAO extends DAO<Customer> {
    public void rentACar(int customerId) throws SQLException, ClassNotFoundException;
    public void returnCar(int customerId) throws SQLException, ClassNotFoundException;
    public void showRentedCar(int customerId) throws SQLException, ClassNotFoundException;
    public Integer getRentedCarId(int customerId) throws SQLException, ClassNotFoundException;

    public int delete(int customerId) throws SQLException, ClassNotFoundException;
    public void setRentCarId(int id, int carId) throws SQLException, ClassNotFoundException;
}
