package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    T get(int id) throws SQLException, ClassNotFoundException;
    List<T> getAll() throws SQLException, ClassNotFoundException;
    int save(T t) throws SQLException, ClassNotFoundException;
    int insert(T t) throws SQLException, ClassNotFoundException;
    int update(T t) throws SQLException, ClassNotFoundException;
    int delete(T t) throws SQLException, ClassNotFoundException;
    int getId() throws SQLException, ClassNotFoundException;

}
