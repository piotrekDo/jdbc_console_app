package app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class ColumnDetails {
    private String tableName;
    private String columnName;
    private String dataType;
    private boolean isForeignKey;
    private String foreignTable;
    private String foreignTableColumnName;

    public ColumnDetails(String tableName, String columnName, String dataType) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
    }
}
