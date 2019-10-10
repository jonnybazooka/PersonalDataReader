package persistence.dao.impl;

import datasoucre.ConnectionSource;
import persistence.dao.CustomerDao;
import persistence.models.Customer;

import java.sql.*;

public class CustomerDaoImpl implements CustomerDao {
    private static final long KEY_NOT_GENERATED = 0;
    private final String INSERT_QUERY = "INSERT INTO customers (NAME, SURNAME, Age) VALUES (?, ?, ?)";

    @Override
    public long saveNewCustomer(String[] rowData) {
        if (rowData.length < 3) {
            return KEY_NOT_GENERATED;
        }
        if (rowData[0].isEmpty() || rowData[1].isEmpty()) {
            return KEY_NOT_GENERATED;
        }
        try (
                final Connection connection = ConnectionSource.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            setParameters(preparedStatement, rowData);
            return executeStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return KEY_NOT_GENERATED;
    }

    @Override
    public long saveNewCustomer(Customer customer) {
        if (customer.getName().isEmpty() || customer.getSurname().isEmpty()) {
            return KEY_NOT_GENERATED;
        }
        try (
                final Connection connection = ConnectionSource.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            setParameters(preparedStatement, customer);
            return executeStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return KEY_NOT_GENERATED;
    }

    private void setParameters(PreparedStatement preparedStatement, String[] rowData) throws SQLException {
        preparedStatement.setString(1, rowData[0]);
        preparedStatement.setString(2, rowData[1]);
        if (rowData[2].matches("\\d{1,3}")) {
            preparedStatement.setInt(3, Integer.parseInt(rowData[2]));
        } else {
            preparedStatement.setNull(3, Types.NULL);
        }
    }

    private void setParameters(PreparedStatement preparedStatement, Customer customer) throws SQLException {
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getSurname());
        if (customer.getAge() != null) {
            preparedStatement.setInt(3, customer.getAge());
        } else {
            preparedStatement.setNull(3, Types.NULL);
        }
    }

    private long executeStatement(PreparedStatement preparedStatement) throws SQLException {
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected == 1) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong(1);
        }
        return KEY_NOT_GENERATED;
    }
}
