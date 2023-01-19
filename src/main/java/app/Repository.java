package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Repository {

    private final Connection connection;

    public Repository(Connection connection) {
        this.connection = connection;
    }

    LinkedList<TableDetails> fetchTableColumnsData(String tableName) {
        String query = getFetchTableDetailsQuery(tableName);
        LinkedList<TableDetails> tableDetails = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            boolean foreignDetails = false;
            while (resultSet.next()) {
                if (resultSet.getString("table_name").equals("FOREIGN_KEY")) {
                    foreignDetails = true;
                    continue;
                }

                if (!foreignDetails) {
                    tableDetails.add(new TableDetails(
                            resultSet.getString("table_name"),
                            resultSet.getString("column_name"),
                            resultSet.getString("data_type")
                    ));
                } else {
                    String foreignKeyColumn = resultSet.getString("table_name");
                    String foreignTableName = resultSet.getString("column_name");
                    String foreignTableColumnName = resultSet.getString("data_type");
                    tableDetails.stream().filter(column -> column.getColumnName().equals(foreignKeyColumn))
                            .findFirst()
                            .ifPresent(column -> {
                                column.setForeignKey(true);
                                column.setForeignTable(foreignTableName);
                                column.setForeignTableColumnName(foreignTableColumnName);
                            });
                }
            }

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        tableDetails.forEach(System.out::println);
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

    public LinkedList<LinkedList<String>> selectDataFromTable(String tableName, LinkedList<TableDetails> tableDetails, int offset, int elements, String sortBy, boolean isDescending) {
        LinkedList<LinkedList<String>> results = new LinkedList<>();
        String sql = String.format("SELECT * FROM %s ORDER BY %s %s LIMIT ?, ?", tableName, sortBy, isDescending ? "DESC" : "ASC");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, offset);
            statement.setInt(2, elements);

            final ResultSet resultSet = statement.executeQuery();
            LinkedList<String> headers = tableDetails.stream().map(TableDetails::getTableName).collect(Collectors.toCollection(LinkedList::new));
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

    private String getFetchTableDetailsQuery(String tableName) {
        return String.format("SELECT table_name, column_name, data_type\n" +
                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE table_schema != 'test' AND TABLE_NAME = '%s'\n" +
                "UNION\n" +
                "SELECT 'FOREIGN_KEY', 'FOREGIN_TABLE', 'FOREGIN_COLUMN' FROM orders\n" +
                "UNION\n" +
                "SELECT\n" +
                "    `column_name`, \n" +
                "    `referenced_table_name`, \n" +
                "    `referenced_column_name`\n" +
                "FROM\n" +
                "    `information_schema`.`KEY_COLUMN_USAGE`\n" +
                "WHERE\n" +
                "    `constraint_schema` = SCHEMA()\n" +
                "AND\n" +
                "    `table_name` = 'orders'\n" +
                "AND\n" +
                "    `referenced_column_name` IS NOT NULL;", tableName);
    }
}