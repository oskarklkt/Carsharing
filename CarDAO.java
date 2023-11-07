package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends DAO<Car>  {
    public List<Car> getCarListByCompanyId(int companyId) throws SQLException, ClassNotFoundException;
    public int getCarIdByName (String name) throws SQLException, ClassNotFoundException;
    public boolean isCarRented(int carID) throws SQLException, ClassNotFoundException;
}
