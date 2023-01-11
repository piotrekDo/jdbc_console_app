package app;

import customer.CustomerEntity;
import customer.CustomerParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Repository {

    private final Connection connection;

    public Repository(Connection connection) {
        this.connection = connection;
    }

    LinkedHashMap<String, String> fetchTableColumnsData(String tableName) {
        LinkedHashMap<String, String> tableDetails = new LinkedHashMap<>();
        try (PreparedStatement statement = connection.prepareStatement("" +
                "SELECT column_name, data_type\n" +
                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE table_schema != 'test' AND TABLE_NAME = ?;")) {
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableDetails.put(resultSet.getString("column_name"), resultSet.getString("data_type"));
            }

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return tableDetails;
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
            CustomerParser customerParser = new CustomerParser(null);
            while (resultSet.next()) {
                customerEntities.add(customerParser.parseToCustomer(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerEntities;
    }

    public LinkedList<LinkedList<String>> selectDataFromTable(String tableName, Map<String, String> tableDetails, int offset, int elements) {
        LinkedList<LinkedList<String>> results = new LinkedList<>();
        String sql = String.format("SELECT * FROM %s LIMIT ?, ?", tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, offset);
            statement.setInt(2, elements);

            final ResultSet resultSet = statement.executeQuery();
            LinkedList<String> headers = new LinkedList<>(tableDetails.keySet());
            results.add(headers);
            while (resultSet.next()) {
                LinkedList<String> row = new LinkedList<>();
                for (int i = 1; i <= tableDetails.size(); i++) {
                    row.add(resultSet.getString(i));
                }
                results.add(row);
            }

            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}