package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends DAO<Car>  {
    public List<Car> getCarListByCompanyId(int companyId) throws SQLException, ClassNotFoundException;
}
