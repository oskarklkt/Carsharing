package carsharing;

import carsharing.Company;
import carsharing.CompanyDAO;
import carsharing.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    //CRUD - Retrieve
    @Override
    public Company get(int id) throws SQLException, ClassNotFoundException  {
        Connection connection = Database.getConnection();
        Company company = null;

        String sql = "SELECT * FROM COMPANY WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int oid = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");

            company = new Company(oid, name);
        }
        connection.close();
        preparedStatement.close();

        return company;
    }

    //CRUD - Retrieve all
    @Override
    public List<Company> getAll() throws SQLException, ClassNotFoundException{
        Connection connection = Database.getConnection();
        Company company = null;
        List<Company> companyList = new ArrayList<>();

        String sql = "SELECT * FROM COMPANY";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");

            companyList.add(new Company(id, name));
        }
        connection.close();

        return companyList;
    }

    //CRUD - Create or update
    @Override
    public int save(Company company) throws SQLException, ClassNotFoundException {
        return 0;
    }

    //CRUD - Create
    @Override
    public int insert(Company company) throws SQLException, ClassNotFoundException {
        Connection connection = Database.getConnection();

        String sql = "INSERT INTO COMPANY (ID, NAME) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, company.getId());
        preparedStatement.setString(2, company.getName());

        int result = preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public int update(Company company) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int delete(Company company) throws SQLException, ClassNotFoundException {
        return 0;
    }

    //This method returns id that should be applied
    public int getId() throws SQLException, ClassNotFoundException{
        return getAll().size() + 1;
    }
}
