package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO extends DAO<Company> {
    public List<Car> getAllCarsOfCompany(int companyId) throws SQLException, ClassNotFoundException;
}
