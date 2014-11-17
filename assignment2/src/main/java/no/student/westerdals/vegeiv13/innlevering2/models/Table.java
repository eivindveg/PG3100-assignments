package no.student.westerdals.vegeiv13.innlevering2.models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private List<Row> knownRows = new ArrayList<>();
    private final List<Column> columns;

    public Table(final String name, final List<Column> columns) {
        this.name = name;
        this.columns = columns;
        boolean columnsHasPrimaryKey = false;
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                columnsHasPrimaryKey = true;
                break;
            }
        }
        if (!columnsHasPrimaryKey) {
            for (Column column : columns) {
                column.setIndex(column.getIndex() + 1);
            }
            addIdColumn();
        }
    }

    private void addIdColumn() {
        Column idColumn = new Column(DataType.BIGINT, 0, 11, "id");
        idColumn.setPrimaryKey(true);
        columns.add(0, idColumn);
    }

    public static Table mapFromResultSet(final ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<Column> columns = new ArrayList<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String columnTypeName = metaData.getColumnTypeName(i);
            int precision = metaData.getPrecision(i);
            Column column = new Column(DataType.getTypeForString(columnTypeName), i, precision, columnName);
            columns.add(column);
            column.setPrimaryKey(metaData.isAutoIncrement(i));
        }
        Table table = new Table(metaData.getTableName(1), columns);
        while (resultSet.next()) {
            Row row = new Row();
            for (Column column : columns) {
                row.put(column, resultSet.getString(columns.indexOf(column) + 1));
            }
            table.addKnownRow(row);
        }
        return table;
    }

    public List<Row> getKnownRows() {
        return knownRows;
    }

    public void setKnownRows(final List<Row> knownRows) {
        this.knownRows = knownRows;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String toCreateTableStatement() {
        String baseStatement = "CREATE TABLE IF NOT EXISTS `" + this.name + "` (?" +
                "PRIMARY KEY (!)\n)" +
                "ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";
        StringBuilder columnBuilder = new StringBuilder();
        for (Column column : columns) {
            columnBuilder.append("`").append(column.getName()).append("` ");
            columnBuilder.append(column.getDataType().name()).append("(").append(column.getWidth()).append(")");
            columnBuilder.append(" NOT NULL");
            if (column.isPrimaryKey()) {
                columnBuilder.append(" AUTO_INCREMENT");
                baseStatement = baseStatement.replace("!", "`" + column.getName() + "`");
            }
            columnBuilder.append(",\n");
        }
        return baseStatement.replace("?", columnBuilder.toString());
    }

    public List<Column> getColumns() {
        return columns;
    }

    private void addKnownRow(final Row row) {
        knownRows.add(row);
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", knownRows=" + knownRows +
                ", columns=" + columns +
                '}';
    }

    public String describe() {
        String output = "Describing table '" + this.name + "':\n";
        for(Column column : columns) {
            output += String.format("%-30s", column.getName());
        }
        output += "\n";
        for(Row row : knownRows) {
            for (Column column : row.keySet()) {
                output += String.format("%-30s", row.get(column));
            }
            output += "\n";
        }
        return output;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void addRow(final Row row) {
        knownRows.add(row);
    }
}
