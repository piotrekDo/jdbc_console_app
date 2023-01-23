package app.repository;

import app.model.ColumnDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Auxiliary class containing methods used in Repository to parse data and create queries.n
 */

public class RepositoryService {

    /**
     * Method used in Repository to parse ResultSet to ColumnDetails list
     */

    LinkedList<ColumnDetails> parseResultSetToTableDetails(ResultSet resultSet) {
        LinkedList<ColumnDetails> columnsDetails = new LinkedList<>();
        boolean foreignDetails = false;
        try {
            while (resultSet.next()) {
                if (resultSet.getString("table_name").equals("FOREIGN_KEY")) {
                    foreignDetails = true;
                    continue;
                }

                if (!foreignDetails) {
                    columnsDetails.add(new ColumnDetails(
                            resultSet.getString("table_name"),
                            resultSet.getString("column_name"),
                            resultSet.getString("data_type")
                    ));
                } else {
                    String foreignKeyColumn = resultSet.getString("table_name");
                    String foreignTableName = resultSet.getString("column_name");
                    String foreignTableColumnName = resultSet.getString("data_type");
                    columnsDetails.stream().filter(column -> column.getColumnName().equals(foreignKeyColumn))
                            .findFirst()
                            .ifPresent(column -> {
                                column.setForeignKey(true);
                                column.setForeignTable(foreignTableName);
                                column.setForeignTableColumnName(foreignTableColumnName);
                            });
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return columnsDetails;
    }

    /**
     * Method used in Repository to build complex, generic query and generate smart joins from foreign keys.
     *
     * @param tableName     name of table to acquire data from.
     * @param columnDetails collection containing ColumnDetails object indicating which column is a foreign key.
     * @param sortBy        name of a column to be sorted by.
     * @param isDescending  direction of sorting.
     * @return method will return a query string used in PrepareStatement.
     */

    public String getSelectDataFromTableQuery(String tableName, LinkedList<ColumnDetails> columnDetails, String sortBy, boolean isDescending) {
        List<ColumnDetails> foreignKeys = columnDetails.stream()
                .filter(x -> x.isForeignKey() && !x.getForeignTable().equals(tableName))
                .toList();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        IntStream.range(0, columnDetails.size()).forEach(i -> {
            ColumnDetails column = columnDetails.get(i);
            if (!column.isForeignKey()) {
                stringBuilder.append(column.getTableName());
                stringBuilder.append(".");
                stringBuilder.append(column.getColumnName());
                stringBuilder.append(", ");
            } else {
                stringBuilder.append(column.getForeignTable());
                stringBuilder.append(".*");
                stringBuilder.append(", ");
            }
        });
        int penultimate = stringBuilder.length() - 2;
        stringBuilder.replace(penultimate, penultimate + 1, " ");
        stringBuilder.append("FROM ");
        stringBuilder.append(tableName);
        if (foreignKeys.size() > 0)
            IntStream.range(0, foreignKeys.size()).forEach(i -> {
                ColumnDetails foreignKey = foreignKeys.get(i);
                stringBuilder.append(" LEFT JOIN ");
                stringBuilder.append(foreignKey.getForeignTable());
                stringBuilder.append(" ON ");
                stringBuilder.append(tableName);
                stringBuilder.append(".");
                stringBuilder.append(foreignKey.getColumnName());
                stringBuilder.append(" = ");
                stringBuilder.append(foreignKey.getForeignTable());
                stringBuilder.append(".");
                stringBuilder.append(foreignKey.getForeignTableColumnName());
            });
        stringBuilder.append(" ORDER BY ");
        stringBuilder.append(sortBy);
        stringBuilder.append(" ");
        stringBuilder.append(isDescending ? "DESC" : "ASC");
        stringBuilder.append(" LIMIT ?, ?;");

        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }
}
