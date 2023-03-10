package app.repository;

import app.model.ColumnDetails;

import java.sql.*;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Repository {

    private final Connection connection;
    private final RepositoryService service;

    /**
     * Repository class used to communicate with database.
     *
     * @param service service class containing methods used to parse data.
     */

    public Repository(Connection connection, RepositoryService service) {
        this.connection = connection;
        this.service = service;
    }

    /**
     * Method returning a collection of ColumnDetail object.
     *
     * @param tableName table name required in order to obtain data.
     * @return ColumnDetail object will contain information regarding table name, column name, foreign key data, ect.
     */

    public LinkedList<ColumnDetails> fetchTableColumnsData(String tableName) {
        String query = getFetchTableDetailsQuery(tableName);
        LinkedList<ColumnDetails> columnDetails = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();

            columnDetails = service.parseResultSetToTableDetails(resultSet);

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        assert columnDetails != null;
        columnDetails.stream().filter(ColumnDetails::isForeignKey).forEach(System.out::println);
        return columnDetails;
    }

    /**
     * Method used to retrieve all tables available within database.
     */

    public LinkedList<String> getAllTableNames() {
        LinkedList<String> results = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(
                    """
                            SELECT TABLE_NAME
                            FROM INFORMATION_SCHEMA.TABLES
                            WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='sql_store';
                            """);


            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Unexpected error occurred " + e.getMessage());
        }
        return results;
    }

    /**
     * Method returning a collection of data from database table.
     *
     * @param tableName      database table to retrieve data from.
     * @param columnsDetails collection of metadata regarding columns within database table.
     * @param offset         number of items skipped, used for pagination.
     * @param elements       number elements to be retrieved, used for pagination.
     * @param sortBy         column name to be sorted by.
     * @param isDescending   sorting direction.
     * @return returns a list of rows. Each row is represented by list containing columns in that row.
     */

    public LinkedList<LinkedList<String>> selectDataFromTable(String tableName, LinkedList<ColumnDetails> columnsDetails, int offset, int elements, String sortBy, boolean isDescending) {
        LinkedList<LinkedList<String>> results = new LinkedList<>();
        String query = service.getSelectDataFromTableQuery(tableName, columnsDetails, sortBy, isDescending);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, elements);

            final ResultSet resultSet = statement.executeQuery();
            final ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            LinkedList<String> headers = new LinkedList<>();
            IntStream.rangeClosed(1, columnCount).forEach(i -> {
                try {
                    headers.add(metaData.getColumnName(i).toUpperCase());
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            });
            results.add(headers);

            while (resultSet.next()) {
                LinkedList<String> row = new LinkedList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                results.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Auxiliary method used to prepare query used in fetchTableColumnsData method.
     *
     * @param tableName table name required to collect data from.
     */

    private String getFetchTableDetailsQuery(String tableName) {
        return String.format(
                """   
                SELECT table_name, column_name, data_type
                FROM INFORMATION_SCHEMA.COLUMNS
                WHERE table_schema != 'test' AND TABLE_NAME = '%s'
                UNION
                SELECT 'FOREIGN_KEY', 'FOREIGN_TABLE', 'FOREIGN_COLUMN' FROM %s
                UNION
                SELECT
                    `column_name`,
                    `referenced_table_name`,
                    `referenced_column_name`
                FROM
                    `information_schema`.`KEY_COLUMN_USAGE`
                WHERE
                    `constraint_schema` = SCHEMA()
                AND
                    `table_name` = '%s'
                AND
                    `referenced_column_name` IS NOT NULL;
                """, tableName, tableName, tableName);
    }
}