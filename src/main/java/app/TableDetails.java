package app;

public class TableDetails {
    private String tableName;
    private String dataType;
    private String columnKey;

    public TableDetails(String tableName, String dataType, String columnKey) {
        this.tableName = tableName;
        this.dataType = dataType;
        this.columnKey = columnKey;
    }

    @Override
    public String toString() {
        return "TableDetails{" +
                "tableName='" + tableName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", columnKey='" + columnKey + '\'' +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
}
