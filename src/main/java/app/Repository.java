package app;

import customer.CustomerEntity;
import customer.CustomerParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class Repository {

    private final Connection connection;

    public Repository(Connection connection) {
        this.connection = connection;
    }

    LinkedList<String> getAllTableNames() {
        LinkedList<String> results = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(
                    "SELECT TABLE_NAME \n" +
                            "FROM INFORMATION_SCHEMA.TABLES\n" +
                            "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='sql_store';");

            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Unexpected error occurred " + e.getMessage());
        }
        return results;
    }


    public LinkedList<CustomerEntity> selectDataFromCustomersTable(int offset, int elements) {
        LinkedList<CustomerEntity> customerEntities = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM customers LIMIT ?, ?;")) {
            statement.setInt(1, offset);
            statement.setInt(2, elements);

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customerEntities.add(CustomerParser.parseToCustomer(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerEntities;
    }
}