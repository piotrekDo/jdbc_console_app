package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class RepositoryService {

    LinkedList<TableDetails> parseResultSetToTableDetails(ResultSet resultSet) {
        LinkedList<TableDetails> tableDetails = new LinkedList<>();
        boolean foreignDetails = false;
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tableDetails;
    }

    public String getSelectDataFromTableQuery(String tableName, LinkedList<TableDetails> tableDetails, String sortBy, boolean isDescending) {
        List<TableDetails> foreignKeys = tableDetails.stream().filter(x -> x.isForeignKey() && !x.getForeignTable().equals(tableName)).toList();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        IntStream.range(0, tableDetails.size()).forEach(i -> {
            TableDetails column = tableDetails.get(i);
            if (!column.isForeignKey()) {
                stringBuilder.append(column.getTableName());
                stringBuilder.append(".");
                stringBuilder.append(column.getColumnName());
                stringBuilder.append(", ");
            } else {
                stringBuilder.append(column.getForeignTable());
                stringBuilder.append(".*");
//                stringBuilder.append(".");
//                stringBuilder.append(column.getForeignTableColumnName());
                stringBuilder.append(", ");
            }
        });
        int penultimate = stringBuilder.length() - 2;
        stringBuilder.replace(penultimate, penultimate + 1, " ");
        stringBuilder.append("FROM ");
        stringBuilder.append(tableName);
        if (foreignKeys.size() > 0)
            IntStream.range(0, foreignKeys.size()).forEach(i -> {
                TableDetails foreignKey = foreignKeys.get(i);
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
