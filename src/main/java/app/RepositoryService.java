package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

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
}
